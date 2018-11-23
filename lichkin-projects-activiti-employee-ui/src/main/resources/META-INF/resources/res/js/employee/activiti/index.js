var _employee_activiti_index = function(serverDatas) {
  LK.initTitle('Workflow Center');

  $('.tab-button').each(function() {
    var key = $(this).attr('id').split('_')[1];
    $(this).html($.LKGetI18N(key)).click(function() {
      activeTab(key, serverDatas);
    });
  });

  activeTab(serverDatas.tabName, serverDatas);
};

var activeTab = function(key, serverDatas) {
  if (key == null) {
    activeTab('pending', serverDatas);
    return;
  }
  $('.tab-button').removeClass('focus');
  $('#tabButton_' + key).addClass('focus');
  $('.tab-content').hide();
  $('#tabContent_' + key).children().remove();
  $('#tabContent_' + key).show();

  window['invoke_' + key]($('#tabContent_' + key), serverDatas);
};

var invoke_apply = function($content, serverDatas) {
  LK.ajax({
    url : '/Activiti/GetProcessList',
    data : {
      platformType : 'EMPLOYEE'
    },
    success : function(responseDatas) {
      for (var i = 0; i < responseDatas.length; i++) {
        var data = responseDatas[i];
        var $item = $('<div class="list-item"><div class="list-item-text">' + data.processName + '</div><div class="list-item-next"><img src="' + _IMG + '/employee/activiti/index/next.png" /></div></div>').appendTo($content);
        (function(processConfigId, processCode) {
          $item.click(function() {
            LK.Go('/employee/activiti/submitForm/index', {
              processCode : processCode,
              processConfigId : processConfigId
            });
          });
        })(data.processConfigId, data.processCode);
      }
    }
  });
};

var invoke_applied = function($content, serverDatas) {
  LK.scrollDatas('applied', '/Activiti/GetFormPage', {}, function(data) {
    var $item = $('<div class="gird-item"></div>').appendTo($content);
    $item.addClass(data.approvalStatus);
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application type:') + '</div><div class="gird-item-value">' + data.processCode + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Approval status:') + '</div><div class="gird-item-value">' + $.LKGetI18N('APPROVAL_STATUS.' + data.approvalStatus) + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application time:') + '</div><div class="gird-item-value">' + showStandardTime(data.insertTime) + '</div><div style="clear:both;"></div>');
    (function(processType, processInstanceId, processCodeDictCode) {
      $item.click(function() {
        LK.Go('/employee/activiti/processDetail/index', {
          tabName : 'applied',
          processType : processType,
          processInstanceId : processInstanceId,
          processCode : processCodeDictCode
        });
      });
    })(data.processType, data.processInstanceId, data.processCodeDictCode);
  });
};

var invoke_pending = function($content, serverDatas) {
  LK.ajax({
    url : '/Activiti/GetPendingProcess',
    success : function(responseDatas) {
      for (var i = 0; i < responseDatas.length; i++) {
        var data = responseDatas[i];
        var $item = $('<div class="gird-item"></div>').appendTo($content);
        $item.addClass('PENDING');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application type:') + '</div><div class="gird-item-value">' + data.processName + '</div><div style="clear:both;"></div>');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application user:') + '</div><div class="gird-item-value">' + data.processStartUserName + '</div><div style="clear:both;"></div>');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application time:') + '</div><div class="gird-item-value">' + data.processStartTime + '</div><div style="clear:both;"></div>');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Task name:') + '</div><div class="gird-item-value">' + data.taskName + '</div><div style="clear:both;"></div>');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Task start time:') + '</div><div class="gird-item-value">' + data.taskStartTime + '</div><div style="clear:both;"></div>');
        (function(processType, processInstanceId, processCode) {
          $item.click(function() {
            LK.Go('/employee/activiti/processDetail/index', {
              tabName : 'pending',
              processType : processType,
              processInstanceId : processInstanceId,
              processCode : processCode
            });
          });
        })(data.processType, data.processInstanceId, data.processCode);
      }
    }
  });
};

var invoke_approved = function($content, serverDatas) {
  LK.scrollDatas('approved', '/Activiti/GetDoneProcess', {}, function(data) {
    var $item = $('<div class="gird-item"></div>').appendTo($content);
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application type:') + '</div><div class="gird-item-value">' + data.processName + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application user:') + '</div><div class="gird-item-value">' + data.processStartUserName + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application time:') + '</div><div class="gird-item-value">' + data.processStartTime + '</div><div style="clear:both;"></div>');
    if (data.processIsEnd) {
      if (data.delReason == null) {
        $item.addClass('APPROVED');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Process end time:') + '</div><div class="gird-item-value">' + data.processEndTime + '</div><div style="clear:both;"></div>');
      } else {
        $item.addClass('REJECT');
        $item.append('<div class="gird-item-key">' + $.LKGetI18N('Reject time:') + '</div><div class="gird-item-value">' + data.processEndTime + '</div><div style="clear:both;"></div>');
      }
    } else {
      $item.addClass('HANDLING');
      $item.append('<div class="gird-item-key">' + $.LKGetI18N('Task name:') + '</div><div class="gird-item-value">' + data.activeTaskName + '</div><div style="clear:both;"></div>');
      $item.append('<div class="gird-item-key">' + $.LKGetI18N('Task start time:') + '</div><div class="gird-item-value">' + data.taskEndTime + '</div><div style="clear:both;"></div>');
    }
    (function(processType, processInstanceId, processCode) {
      $item.click(function() {
        LK.Go('/employee/activiti/processDetail/index', {
          tabName : 'approved',
          processType : processType,
          processInstanceId : processInstanceId,
          processCode : processCode
        });
      });
    })(data.processType, data.processInstanceId, data.processCode);
  });
};