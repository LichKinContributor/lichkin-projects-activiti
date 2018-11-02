LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'activitiProcessTaskConfigMgmt',
} : {}), {
  i18nKey : 'activitiProcessTaskConfigMgmt',
  $appendTo : true,
  cols : 2.5,
  url : '/SysActivitiProcessConfig/P01',
  param : {
    usingStatus : 'USING'
  },
  columns : [
      {
        text : 'processName',
        width : '1/3',
        name : 'processName'
      }, {
        text : 'deptName',
        width : '2/3',
        formatter : function(rowData, $plugin, options, $container, level, columns, treeFieldName, i18nKey) {
          if (rowData.deptName) {
            return rowData.deptName;
          }
          return $.LKGetI18N(i18nKey + 'deptNameAll');
        }
      }
  ],
  tools : [
    {
      singleCheck : true,
      icon : 'set',
      text : 'set',
      click : function($button, $plugin, $selecteds, selectedDatas, value, i18nKey) {
        LK.ajax({
          url : '/SysActivitiProcessTaskConfig/L',
          data : {
            configId : value,
            withoutFirst : true
          },
          success : function(data) {
            var rows = data.length;
            var $configFormDlg = LK.UI.openDialog($.extend({}, {
              size : {
                cols : 2,
                rows : rows
              }
            }, {
              title : 'set',
              icon : 'set',
              mask : true,
              buttons : [
                  {
                    text : 'save',
                    icon : 'save',
                    cls : 'warning',
                    click : function($button, $dialog) {
                      var $form = $configFormDlg.find('form');
                      if ($form.LKValidate()) {
                        var userNameArr = [];
                        $form.find('input[name=employeeId]').siblings('.lichkin-selector-wrapper').find('.lichkin-text').each(function() {
                          userNameArr.push($(this).html());
                        });

                        LK.ajax({
                          url : '/SysActivitiProcessTaskConfig/S01',
                          data : $.extend({
                            id : value,
                            userName : userNameArr.join(LK.SPLITOR)
                          }, $form.LKFormGetData()),
                          showSuccess : true,
                          success : function() {
                            $plugin.LKLoad({
                              param : LK.UI._datagrid.getParam($plugin, $plugin.data('LKOPTIONS'))
                            });
                            $configFormDlg.LKCloseDialog();
                          }
                        });
                      }
                    }
                  }, {
                    text : 'cancel',
                    icon : 'cancel',
                    cls : 'danger',
                    click : function($button, $dialog) {
                      $configFormDlg.LKCloseDialog();
                    }
                  }
              ],
              onAfterCreate : function($dialog, $contentBar) {
                var ids = [];
                var plugins = [];
                for (var i = 0; i < data.length; i++) {
                  ids.push(data[i].id);
                  plugins.push({
                    plugin : 'textbox',
                    options : {
                      key : i18nKey + 'taskStepName',
                      keyTextReplaces : [
                        {
                          regex : 'N',
                          replacement : i + 1
                        }
                      ],
                      name : 'taskName',
                      value : (data[i].taskName ? data[i].taskName : $.LKGetI18N(i18nKey + 'taskStepName').replace('N', i + 1)),
                      validator : true
                    }
                  }, {
                    plugin : 'selector_employee',
                    options : {
                      key : i18nKey + 'approver',
                      name : 'employeeId',
                      value : data[i].loginId,
                      validator : true
                    }
                  });
                }

                plugins.push({
                  plugin : 'hidden',
                  options : {
                    name : 'taskId',
                    value : ids.join(LK.SPLITOR)
                  }
                });

                LK.UI.form({
                  plugins : plugins,
                  $appendTo : $contentBar
                });
              }
            }));
          }
        });
      }
    }
  ],
  searchForm : [
    {
      plugin : 'textbox',
      options : {
        name : 'processName',
        cls : 'fuzzy-left fuzzy-right'
      }
    }
  ]
}));
