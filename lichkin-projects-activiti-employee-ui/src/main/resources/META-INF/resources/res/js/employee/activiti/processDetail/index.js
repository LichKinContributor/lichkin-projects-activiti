var _employee_activiti_processDetail_index = function(serverDatas) {
  LK.initTitle('Approval Detail', '/employee/activiti/index', {
    tabName : serverDatas.tabName
  });

  var width = $('body').width() - LK.fieldKeyWidth - 30;

  $('.tab-button').each(function() {
    var key = $(this).attr('id').split('_')[1];
    $(this).html($.LKGetI18N(key.replace('0', ' '))).click(function() {
      activeTab(key, serverDatas);
    });
  });

  // 初始化保存按钮
  var $saveButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'save',
    cls : 'success disable',
    style : {
      width : '32%'
    }
  });

  // 初始化提交按钮
  var $submitButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'approve',
    cls : 'warning disable',
    style : {
      'width' : '32%',
      'margin-left' : '1%'
    }
  });

  // 初始化驳回按钮
  LK.UI.button({
    $appendTo : $('.button'),
    text : 'reject',
    cls : 'danger',
    style : {
      'width' : '32%',
      'margin-left' : '1%'
    },
    click : function() {
      LK.web.confirm('The process will be ended', function() {
        LK.ajax({
          url : '/Activiti/RejectProcess',
          data : $.extend({
            comment : '驳回'
          }, serverDatas),
          success : function(responseDatas) {
            LK.alert('Reject successfully');
            LK.Go('/employee/activiti/index', {
              tabName : 'pending'
            })
          }
        });
      });
    }
  });

  LK.ajax({
    url : '/Activiti/GetDetailProcess',
    data : serverDatas,
    success : function(processDetailData) {
      var currentStep = 0;
      var reject = false;

      var startInfo = processDetailData[0];
      $('.title .title-text').html(startInfo.processName + '（' + startInfo.processStartUserName + '）');

      var $content = $('.timeline ul');
      $('<li class="start"><div class="timeline-point"></div><div class="timeline-content-container">' + $.LKGetI18N('start') + '</div></li>').appendTo($content);
      for (var i = 1; i < processDetailData.length; i++) {
        var data = processDetailData[i];
        var $li = $('<li></li>').appendTo($content);
        var $point = $('<div class="timeline-point"></div>').appendTo($li);
        var $liContent = $('<div class="timeline-content-container"></div>').appendTo($li);
        $liContent.append('<div class="item"><div class="item-key">' + $.LKGetI18N('task name:') + '</div><div class="item-value">' + data.taskName + '</div></div>');
        $liContent.append('<div class="item"><div class="item-key">' + $.LKGetI18N('name:') + '</div><div class="item-value">' + data.approveUserName + '</div></div>');
        if (data.taskStartTime) {
          $liContent.addClass('started');
          $point.addClass('started');
          $liContent.append('<div class="item"><div class="item-key">' + $.LKGetI18N('start time:') + '</div><div class="item-value">' + data.taskStartTime + '</div></div>');
          if (data.taskEndTime) {
            $liContent.addClass('ended');
            $point.addClass('ended');
            $liContent.append('<div class="item"><div class="item-key">' + $.LKGetI18N('end time:') + '</div><div class="item-value">' + data.taskEndTime + '</div></div>');
            if (data.deleteReason != null) {
              $liContent.addClass('reject');
              $point.addClass('reject');
              $liContent.append('<div class="item"><div class="item-key">' + $.LKGetI18N('reject reason:') + '</div><div class="item-value">' + data.deleteReason + '</div></div>');
              reject = true;
            }
          } else {
            currentStep = i;
          }
        }
        $liContent.append('<div style="clear:both;"></div>');
      }
      var $endPoint = $('<li class="end"><div class="timeline-point"></div><div class="timeline-content-container">' + $.LKGetI18N('end') + '</div></li>').appendTo($content);
      if (currentStep == 0) {
        $endPoint.addClass(reject ? 'reject' : 'finished');
      }

      LK.ajax({
        url : '/Activiti/GetFormDataStep',
        data : {
          id : processDetailData[0].formDataId
        },
        success : function(formDataStepData) {
          var $contentForm = $('#tabContent_approval0form');
          for (var i = 0; i < formDataStepData.length; i++) {
            var data = formDataStepData[i];
            var $container = $('<div class="form-data-container"></div>').appendTo($contentForm);
            var $title = $('<div class="form-title">' + data.taskName + '</div>').appendTo($container);
            var $formContainer = $('<div class="form-container"></div>').appendTo($container);
            var plugins = JSON.parse(data.formJson);
            if (serverDatas.tabName != 'pending' || i != currentStep) {
              for (var j = 0; j < plugins.length; j++) {
                plugins[j].options.readonly = true;
                plugins[j].options.width = width;
              }
              LK.UI.form({
                $appendTo : $formContainer,
                plugins : plugins,
                values : JSON.parse(data.dataJson)
              });
            } else {
              for (var j = 0; j < plugins.length; j++) {
                plugins[j].options.width = width;
              }
              var $form = LK.UI.form({
                $appendTo : $formContainer,
                plugins : plugins,
                values : JSON.parse(data.dataJson)
              });
              $title.addClass('active');
              $saveButton.removeClass('disable').click(function() {
                $submitButton.addClass('disable').unbind('click');
                if (!$form.LKValidate()) {
                  LK.alert('Some field incorrect');
                  return;
                }

                LK.ajax({
                  url : '/Activiti/SubmitForm',
                  data : {
                    formDataId : processDetailData[0].formDataId,
                    step : currentStep + 1,
                    dataJson : JSON.stringify($form.LKFormGetData())
                  },
                  success : function(responseDatas) {
                    $submitButton.removeClass('disable').click(function() {
                      LK.ajax({
                        url : '/Activiti/CompleteProcess',
                        data : serverDatas,
                        success : function(responseDatas) {
                          LK.alert('Approve successfully');
                          LK.Go('/employee/activiti/index', {
                            tabName : 'pending'
                          })
                        }
                      });
                    });
                  }
                });
              });
            }
          }
        }
      });
    }
  });

  activeTab(null, serverDatas);
};

var activeTab = function(key, serverDatas) {
  if (key == null) {
    activeTab(serverDatas.tabName == 'pending' ? 'approval0form' : 'approval0process', serverDatas);
    return;
  }
  $('.tab-button').removeClass('focus');
  $('#tabButton_' + key).addClass('focus');
  $('.tab-content').hide();
  $('#tabContent_' + key).show();

  window['invoke_' + key]($('#tabContent_' + key), serverDatas);
};

var invoke_approval0process = function($content, serverDatas) {
  $('.button').hide();
};

var invoke_approval0form = function($content, serverDatas) {
  if (serverDatas.tabName == 'pending') {
    $('.button').show();
  }
};
