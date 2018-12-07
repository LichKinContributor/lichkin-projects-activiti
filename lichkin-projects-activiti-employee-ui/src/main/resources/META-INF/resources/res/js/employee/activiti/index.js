LK.initTitle('Workflow Center');

var invoke_pending = function($content) {
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

var invoke_apply = function($content) {
  LK.ajax({
    url : '/Activiti/GetProcessList',
    data : {
      platformType : 'EMPLOYEE'
    },
    success : function(responseDatas) {
      $(responseDatas).each(function() {
        this.linkUrlPrefix = '/employee/activiti/submitForm/index';
        this.linkUrl = '';
        this.param = {
          tabName : 'apply',
          processCode : this.processCode,
          processConfigId : this.processConfigId
        };
      });

      LK.createItems($content, responseDatas, true, {
        title : 'processName',
      });
    }
  });
};

var invoke_applied = function($content) {
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

var invoke_approved = function($content) {
  LK.scrollDatas('approved', '/Activiti/GetDoneProcess', {}, function(data) {
    var $item = $('<div class="gird-item"></div>').appendTo($content);
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application type:') + '</div><div class="gird-item-value">' + data.processName + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application user:') + '</div><div class="gird-item-value">' + data.processStartUserName + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Application time:') + '</div><div class="gird-item-value">' + data.processStartTime + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Done Task name:') + '</div><div class="gird-item-value">' + data.taskName + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Done Task start time:') + '</div><div class="gird-item-value">' + data.taskStartTime + '</div><div style="clear:both;"></div>');
    $item.append('<div class="gird-item-key">' + $.LKGetI18N('Done Task end time:') + '</div><div class="gird-item-value">' + data.taskEndTime + '</div><div style="clear:both;"></div>');
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

$('.tab-button').each(function() {
  var key = $(this).attr('id').split('_')[1];
  $(this).html($.LKGetI18N(key)).click(function() {
    activeTab(key);
  });
});

var activeTab = function(key) {
  if (!key) {
    activeTab('pending');
    return;
  }

  $('.tab-button').removeClass('focus');
  $('#tabButton_' + key).addClass('focus');
  $('.tab-content').hide();
  $('#tabContent_' + key).children().remove();
  $('#tabContent_' + key).show();

  window['invoke_' + key]($('#tabContent_' + key));
};

activeTab(serverDatas.tabName);