var activitiProcessConfigMgmtFormPlugins = [
    {
      plugin : 'droplist',
      options : {
        key : 'compName',
        name : 'compId',
        url : '/SysComp/LD',
        validator : true,
        value : '20180101000000000_t_sys_comp_000000000000000000000000000_lichkin',
        linkages : [
          'deptId'
        ]
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'platformType',
        param : {
          categoryCode : 'PLATFORM_TYPE'
        },
        validator : true
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'processCode',
        param : {
          categoryCode : 'ACTIVITI_PROCESS_CODE'
        },
        validator : true,
        linkages : [
          'processName'
        ]
      }
    }, {
      plugin : 'textbox',
      options : {
        name : 'processName',
        validator : true,
        maxlength : 64,
        onLinkaged : function($plugin, linkage) {
          if (linkage.isCreateEvent) {
            return;
          }
          switch (linkage.linkageName) {
            case 'processCode':
              if (linkage.linkageValue == '') {
                $plugin.LKClearDatas();
              } else {
                $plugin.LKSetValues(linkage.$linkage.LKGetText())
              }
              break;
          }
        }
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'processKey',
        param : {
          categoryCode : 'ACTIVITI_PROCESS_KEY'
        },
        validator : true,
        value : 'GeneralSingleLineProcess'
      }
    }, {
      plugin : 'droplist',
      options : {
        name : 'processType',
        param : {
          categoryCode : 'ACTIVITI_PROCESS_TYPE'
        },
        validator : true,
        value : 'SINGLE_LINE'
      }
    }, {
      plugin : 'numberspinner',
      options : {
        name : 'stepCount',
        validator : true,
        value : 3,
        min : 1,
        max : 9
      },
    }, {
      plugin : 'tree',
      options : {
        key : 'deptName',
        name : 'deptId',
        checkbox : false,
        multiSelect : false,
        i18nText : false,
        url : '/SysDept/S',
        onLinkaged : function($plugin, linkage) {
          switch (linkage.linkageName) {
            case 'compId':
              if (linkage.linkageValue == '') {
                $plugin.LKClearDatas();
              } else {
                $plugin.LKLoad({
                  param : {
                    compId : linkage.linkageValue
                  }
                }, linkage);
              }
              break;
          }
        }
      }
    }
];

LK.UI.datagrid($.extend((typeof LK.home == 'undefined' ? {
  title : 'title',
  icon : 'activitiProcessConfigMgmt',
} : {}), {
  i18nKey : 'activitiProcessConfigMgmt',
  $appendTo : true,
  cols : 4,
  url : '/ROOT_GetActivitiProcessConfigPage',
  columns : [
      {
        text : 'compName',
        width : 200,
        name : 'compName'
      }, {
        text : 'deptName',
        width : null,
        formatter : function(rowData, $plugin, options, $container, level, columns, treeFieldName, i18nKey) {
          if (rowData.deptName) {
            return rowData.deptName;
          }
          return $.LKGetI18N(i18nKey + 'all departments');
        }
      }, {
        text : 'platformType',
        width : 100,
        name : 'platformType'
      }, {
        text : 'processCode',
        width : 130,
        name : 'processCode'
      }, {
        text : 'processName',
        width : 130,
        name : 'processName'
      }, {
        text : 'processKey',
        width : 100,
        name : 'processKey'
      }, {
        text : 'processType',
        width : 120,
        name : 'processType'
      }, {
        text : 'usingStatus',
        width : 100,
        name : 'usingStatus'
      }
  ],
  toolsAdd : {
    saveUrl : '/ROOT_AddActivitiProcessConfig',
    dialog : {
      size : {
        cols : 2,
        rows : 14
      }
    },
    form : {
      plugins : activitiProcessConfigMgmtFormPlugins
    }
  },
  toolsEdit : {
    saveUrl : '/ROOT_UpdateActivitiProcessConfig',
    dialog : {
      size : {
        cols : 2,
        rows : 14
      }
    },
    form : {
      url : '/ROOT_GetActivitiProcessConfig',
      plugins : activitiProcessConfigMgmtFormPlugins
    },
    readonlyPlugins : function() {
      return [
          'compId', 'platformType', 'processCode', 'processKey', 'processType', 'deptId'
      ];
    },
    beforeClick : function($button, $datagrid, $selecteds, selectedDatas, value, i18nKey) {
      if (selectedDatas.usingStatusDictCode != 'LOCKED') {
        LK.alert(i18nKey + 'only locked status can be edit');
        return false;
      }
      return true;
    }
  },
  toolsRemove : {
    saveUrl : '/ROOT_DeleteActivitiProcessConfig',
    disallowUsingStatusArr : [
      {
        usingStatus : 'USING',
        errorMsg : 'using status can not be remove'
      }
    ]
  },
  toolsView : {
    dialog : {
      size : {
        cols : 2,
        rows : 14
      }
    },
    form : {
      url : '/ROOT_GetActivitiProcessConfig',
      plugins : activitiProcessConfigMgmtFormPlugins
    }
  },
  toolsUS : [
    {
      icon : 'release',
      text : 'release',
      saveUrl : '/ROOT_PublishActivitiProcessConfig',
      usingStatus : 'USING',
      disallowUsingStatusArr : [
          {
            usingStatus : 'USING',
            errorMsg : 'already using'
          }, {
            usingStatus : 'LOCKED',
            errorMsg : 'must config form before'
          }
      ]
    }
  ],
  tools : [
      {
        singleCheck : true,
        icon : 'set',
        text : 'config form',
        click : function($button, $plugin, $selecteds, selectedDatas, value, i18nKey) {
          if (selectedDatas.usingStatusDictCode == 'USING') {
            LK.alert(i18nKey + 'using status can not be config');
            return false;
          }
          LK.ajax({
            url : '/ROOT_GetActivitiProcessTaskConfigList',
            data : {
              configId : value
            },
            success : function(data) {
              var $configFormDlg = LK.UI.openDialog($.extend({}, {}, {
                size : {
                  cols : 5,
                  rows : data.length * 2
                },
                title : i18nKey + 'config form',
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
                          LK.ajax({
                            url : '/ROOT_ConfigActivitiProcessConfig',
                            data : $form.LKFormGetData(),
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
                  var plugins = [
                      {
                        plugin : 'hidden',
                        options : {
                          name : 'id',
                          value : value
                        }
                      }, {
                        plugin : 'hidden',
                        options : {
                          name : 'usingStatus',
                          value : 'STAND_BY'
                        }
                      }
                  ];

                  for (var i = 0; i < data.length; i++) {
                    plugins.push({
                      plugin : 'textbox',
                      options : {
                        key : i18nKey + 'step N',
                        keyTextReplaces : [
                          {
                            regex : 'N',
                            replacement : i + 1
                          }
                        ],
                        name : 'formJson',
                        value : data[i].formJson,
                        validator : true,
                        cols : 5,
                        rows : 2
                      }
                    }, {
                      plugin : 'hidden',
                      options : {
                        name : 'taskId',
                        value : data[i].id
                      }
                    });
                  }

                  LK.UI.form({
                    plugins : plugins,
                    $appendTo : $contentBar
                  });
                }
              }));
            }
          });
        }
      }, {
        singleCheck : true,
        icon : 'set',
        text : 'reconfig form',
        click : function($button, $plugin, $selecteds, selectedDatas, value, i18nKey) {
          if (selectedDatas.usingStatusDictCode != 'USING') {
            LK.alert(i18nKey + 'not using status can not be reconfig');
            return false;
          }
          LK.ajax({
            url : '/ROOT_GetActivitiProcessTaskConfigList',
            data : {
              configId : value
            },
            success : function(data) {
              var $configFormDlg = LK.UI.openDialog($.extend({}, {}, {
                size : {
                  cols : 5,
                  rows : data.length * 2
                },
                title : i18nKey + 'reconfig form',
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
                          LK.ajax({
                            url : '/ROOT_ReconfigActivitiProcessConfig',
                            data : $form.LKFormGetData(),
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
                  var plugins = [
                      {
                        plugin : 'hidden',
                        options : {
                          name : 'id',
                          value : value
                        }
                      }, {
                        plugin : 'hidden',
                        options : {
                          name : 'usingStatus',
                          value : 'DEPRECATED'
                        }
                      }
                  ];

                  for (var i = 0; i < data.length; i++) {
                    plugins.push({
                      plugin : 'textbox',
                      options : {
                        key : i18nKey + 'step N',
                        keyTextReplaces : [
                          {
                            regex : 'N',
                            replacement : i + 1
                          }
                        ],
                        name : 'formJson',
                        value : data[i].formJson,
                        validator : true,
                        cols : 5,
                        rows : 2
                      }
                    });
                  }

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
          name : 'compName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'droplist',
        options : {
          name : 'platformType',
          param : {
            categoryCode : 'PLATFORM_TYPE'
          }
        }
      }, {
        plugin : 'textbox',
        options : {
          name : 'processName',
          cls : 'fuzzy-left fuzzy-right'
        }
      }, {
        plugin : 'droplist',
        options : {
          name : 'usingStatus',
          param : {
            categoryCode : 'ACTIVITI_USING_STATUS',
            includes : [
                'LOCKED', 'STAND_BY', 'USING'
            ].join(LK.SPLITOR)
          }
        }
      }
  ]
}));
