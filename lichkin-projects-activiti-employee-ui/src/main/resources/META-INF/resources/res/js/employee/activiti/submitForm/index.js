var _submitForm_index = function(serverDatas) {
  // 初始化标题栏
  LK.initTitle([
      serverDatas.processCode, 'Apply for'
  ], '/activitiCenter/index', {
    tabName : 'apply'
  });

  var width = $('body').width() - LK.fieldKeyWidth - 30;

  // 初始化保存按钮
  var $saveButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'save',
    cls : 'success disable',
    style : {
      width : '48%'
    }
  });

  // 初始化提交按钮
  var $submitButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'submit',
    cls : 'warning disable',
    style : {
      'width' : '48%',
      'margin-left' : '1%'
    }
  });

  // 获取表单配置信息
  LK.ajax({
    url : '/Activiti/GetProcessTaskForm',
    apiSubUrl : '/Employee',
    data : {
      processConfigId : serverDatas.processConfigId,
      step : 1
    },
    success : function(responseDatas) {
      var plugins = JSON.parse(responseDatas.formJson);
      plugins.unshift({
        plugin : 'hidden',
        options : {
          name : 'id'
        }
      }, {
        plugin : 'hidden',
        options : {
          name : 'processConfigId',
          required : true
        }
      }, {
        plugin : 'hidden',
        options : {
          name : 'processCode',
          required : true
        }
      }, {
        plugin : 'hidden',
        options : {
          name : 'userId',
          required : true
        }
      }, {
        plugin : 'textbox',
        options : {
          readonly : true,
          name : 'userName',
          key : 'User Name',
          required : true
        }
      }, {
        plugin : 'textbox',
        options : {
          readonly : true,
          name : 'deptName',
          key : 'Department',
          required : true
        }
      });

      for (var j = 0; j < plugins.length; j++) {
        plugins[j].options.width = width;
      }

      $form = LK.UI.form({
        $appendTo : $('.content'),
        plugins : plugins,
        onAfterCreate : function() {
          $saveButton.removeClass('disable').click(function() {
            $submitButton.addClass('disable').unbind('click');
            if (!$form.LKValidate()) {
              LK.alert('Some field incorrect');
              return;
            }

            LK.ajax({
              url : '/Activiti/SubmitForm',
              apiSubUrl : '/Employee',
              data : {
                formDataId : formDataId,
                step : 1,
                dataJson : JSON.stringify($form.LKFormGetData()),
                formTypeCode : serverDatas.processCode,
                processConfigId : serverDatas.processConfigId
              },
              success : function(responseDatas) {
                formDataId = responseDatas.id;
                $submitButton.removeClass('disable').click(function() {
                  LK.ajax({
                    url : '/Activiti/StartProcess',
                    apiSubUrl : '/Employee',
                    data : $.extend({}, $form.LKFormGetData(), {
                      businessKey : responseDatas.id
                    }),
                    success : function(responseDatas) {
                      LK.alert('Submit successfully');
                      LK.Go('/activitiCenter/index', {
                        tabName : 'applied'
                      })
                    }
                  });
                });
              }
            });
          });
        }
      });

      $form.LKFormBindData(serverDatas);
    }
  });
};

var formDataId = '';
