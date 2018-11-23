<#include "/macro/html-lichkin-simple.ftl"/>

<@html css=true i18nJs=true;section>
	<#if section="javascript-links">
		<@lichkin@jsTag url="/res/js/activiti/${processCode}/${locale}" />
	</#if>
	<#if section="body-content">
		<div class="title-back"><img src="${ctx}/res/img/back.png" /></div>
		<div class="title">
			<div class="title-text"></div>
		</div>
		<div class="tabs">
			<div class="tab-button" id="tabButton_approval0process"></div>
			<div class="tab-button" id="tabButton_approval0form"></div>
			<div style="clear:both;"></div>
		</div>
		<div class="tab-content" id="tabContent_approval0process">
			<div class="timeline">
				<div class="timeline-line"></div>
				<ul></ul>
			</div>
		</div>
		<div class="tab-content" id="tabContent_approval0form"></div>
		<div class="button" style="display:none;"></div>
	</#if>
</@html>
