<#include "/macro/html-lichkin-simple.ftl"/>

<@html i18nJs=true;section>
	<#if section="javascript-links">
		<@lichkin@jsTag url="/res/js/employee/activiti/submitForm/${processCode}/i18n/${locale}" />
	</#if>
	<#if section="body-content">
		<div class="title-back"><img src="${ctx}/res/img/back.png" /></div>
		<div class="title">
			<div class="title-text"></div>
		</div>
		<div class="content">
		</div>
		<div class="button"></div>
	</#if>
</@html>
