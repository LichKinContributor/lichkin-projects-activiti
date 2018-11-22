var _employee_activiti_submitForm_index = function(serverDatas) {
  // 初始化标题栏
  LK.initTitle([
      serverDatas.processCode, 'Apply for'
  ], '/employee/activiti/index', {
    tabName : 'apply'
  });

  var width = $('body').width() - LK.fieldKeyWidth - 30;

  // 初始化保存按钮
  var $saveButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'save form',
    cls : 'success disable',
    style : {
      width : '48%'
    }
  });

  // 初始化提交按钮
  var $submitButton = LK.UI.button({
    $appendTo : $('.button'),
    text : 'submit form',
    cls : 'warning disable',
    style : {
      'width' : '48%',
      'margin-left' : '1%'
    }
  });

  // 获取表单配置信息
  LK.ajax({
    url : '/Activiti/GetProcessTaskForm',
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
          validator : true
        }
      }, {
        plugin : 'hidden',
        options : {
          name : 'processCode',
          validator : true
        }
      }, {
        plugin : 'hidden',
        options : {
          name : 'userId',
          validator : true
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
              data : {
                formDataId : formDataId,
                step : 1,
                dataJson : JSON.stringify($form.LKFormGetData()),
                processCode : serverDatas.processCode,
                processConfigId : serverDatas.processConfigId
              },
              success : function(responseDatas) {
                formDataId = responseDatas.id;
                $submitButton.removeClass('disable').click(function() {
                  LK.ajax({
                    url : '/Activiti/StartProcess',
                    data : $.extend({}, $form.LKFormGetData(), {
                      formDataId : responseDatas.id
                    }),
                    success : function(responseDatas) {
                      LK.alert('Submit successfully');
                      LK.Go('/employee/activiti/index', {
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
