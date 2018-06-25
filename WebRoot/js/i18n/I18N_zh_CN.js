// 英文的JS常量
var JS_I18N = {
	// common,框架JS代码里的国际化内容
	common_not_space : '不允许只输入空格！',
	input_error : '输入错误！',
	format_illegal : '输入的格式不正确！',
	please_input : '请输入内容！',
	input_ok : '输入正确！',
	input_is_null : '输入内容为空！',
	wdatePicker_is_null : '不能为空！',
	wdatePicker_is_correct : '合法！',

	// ---------------创建工单--------------------
	issue_please_input_request_user : '请输入请求用户！',
	issue_request_user_Not_null : '请求用户非必填，最大长度为20！',
	issue_legal : '合法！',
	issue_please_select_expect_end_time : '请选择期望完成时间！',
	issue_Not_null : '必填！',
	issue_please_input_title : '请输入标题！',
	issue_title_error : '标题非必填, 最多100个字符！',

	// ----------------创建事件工单--------------
	incident_please_input_type : '请选择工单类型！',
	incident_incident_type_Not_null : '类型不得为空！',
	incident_please_input_title : '请输入标题！',
	incident_incident_tile_Information : '标题可以不输入！',
	incident_incident_tile_Too_long : '标题不超过100个字符',
	incident_please_select_expected_time : '请选择期望完成时间！',
	incident_expected_time_not_null : '期望完成时间不能为空！',

	// ---------------资产模块--------------------
	asset_property_name_not_null : '不得为空且不超过50字符！',
	asset_property_type_not_null : '资产类型不得为空！',
	asset_property_price_not_null : '不得为空且最大值为999999999！',
	asset_property_purchase_date_not_null : '购买日期为非必填项！',
	asset_property_warranty_not_null : '保修期为非必填项！',
	asset_property_name_input : '请填写资产名称！',
	asset_property_type_input : '请填写资产类型！',
	asset_property_price_input : '请填写价格！',
	asset_property_purchase_date_input : '请填写购买日期！',
	asset_property_warranty_input : '请填写保修期！',
	asset_property_price_too_long : '错误，价格不能超过999999999！',
	asset_property_price_too_small : '错误，价格不能低于0.01！',
	asset_property_must_number : '错误，只能输入数字！',
	asset_property_long_256 : '错误，长度不能超过255！',
	asset_property_long_250 : '错误，长度不能超过250！',
	asset_property_long_96 : '错误，长度不能超过100个字符！',
	asset_property_long_20000 : '错误，长度不能超过20000！',
	asset_property_long_500 : '错误，不能超过500！',
	asset_property_min_1 : "错误，不能低于1!",
	asset_property_place_is_null : "位置为非必填项！",
	asset_property_sequence_is_null : "序列号为非必填项！",
	asset_property_description_is_null : "资产描述为非必填项！",
	asset_property_memo_is_null : "备注为非必填项！",
	asset_property_quantity_is_null : "入库数量最小值为1！",
	asset_property_quantity_is_integer : "错误，只能输入正整数！",
	asset_property_code_is_null : "资产编码为非必填项！",
	asset_supplier : "供应商",
	asset_manufacturer : "生产商",
	asset_contract : "合同",
	asset_principal : "负责人",
	asset_typeName : "资产类型",
	asset_user : "使用者",
	asset_department : "部门",
	
	asset_apply_not_null:"不能为空！",
	asset_apply_input_title:"请输入标题！",
	asset_apply_input_memo:"请输入描述！",
	asset_apply_long_100:"错误,长度不能超过100个字符！",
	asset_apply_long_20000:"错误,长度不能超过20000个字符！",
	asset_apply_long_255:'错误，长度不能超过255个字符！',
	
	asset_apply_assetCode_input_assetCode:"请输入资产编号！",
	asset_apply_assetName_input_assetName:"请输入资产名称！",
	asset_apply_assetTypeId_input_assetTypeId:"请选择资产类型！",
	asset_apply_assetTypeId_not_null:"资产类型不能为空！",
	asset_apply_assetCode_long_100:"资产编号长度不能超过100！",
	asset_apply_assetName_long_100:"资产名称长度不能超过100！",
	asset_apply_assetTypeId_long_100:"资产类型长度不能超过100！",
	asset_apply_approveOfficer_input_approveOfficer:"请选择审批人！",
	asset_apply_approveOfficer_not_null:"审批人不能为空！",
	asset_apply_approveOfficer_long_100:"审批人不能超过100！",
	asset_apply_comment_long_20000:"备注长度不能超过20000！",
	asset_apply_comment_input_assetTypeId:"请输入备注！",
	
	
	// ---------------配置工单类型--------------------
	inc : "事件",
	pro : "问题",
	rfc : "变更",
	rel : "发布",
	type : "类型",
	name : "名称",
	memo : "描述",
	type_name_not_null : '类型名不能为空！',
	type_name_length_validate : '类型名长度不能超过50！',
	// ---------------模块分割线--------------------
	// ---------------公告模块--------------------
	notice_property_name_input : '请输入公告标题！',
	notice_property_name_not_null : '公告标题不能为空，且不超过35个字符！',
	notice_property_name_max : '公告标题长度不能超过35',
	notice_property_memo_input : '请输入公告内容！',
	notice_property_memo_not_null : '公告内容不能为空！',
	notice_property_memo_max : '公告内容长度不能超过20000',

	// ---------------邮件模块--------------------
	mail_property_address_input : '请输入邮件发送人地址！',
	mail_property_address_not_null : '邮件发送人地址不能为空！',
	mail_property_address_format_error : '邮件地址格式错误！',
	mail_property_port_input : '请输入邮件端口！',
	mail_property_port_not_null : '邮件端口不能为空！',
	mail_property_port_is_number : '邮件端口必须为数字！',
	mail_property_port_max : '邮件端口号不超过65536',
	mail_property_smtpServer_input : '请输入SMTP服务器！',
	mail_property_smtpServer_not_null : 'SMTP服务器不能为空！',
	mail_property_smtpServer_format_error : 'SMTP服务器格式错误！',
	mail_property_user_input : '请输入用户名！',
	mail_property_user_not_null : '用户名不能为空！',
	mail_property_user_max : '用户名长度不能多于30位！',
	mail_property_password_input : '请输入密码！',
	mail_property_password_not_null : '密码不能为空！',
	mail_property_password_min : '密码长度不能少于1位！',
	mail_property_password_max : '密码长度不能多于30位！',

	/** 变更工单* */
	// -----------------提交-------------------------------------//
	rfc_transition_title_rfc_pass : '同意变更',// 评估同意
	rfc_transition_title_rfc_resubmit : '重新提交工单',
	rfc_transition_title_rfc_reject : '否决变更',// 评估否决
	rfc_transition_title_transition_resolve : '输入实施反馈意见',
	rfc_transition_title_rfc_check_pass : '评审通过',
	rfc_transition_title_transition_reopen : '评审拒绝',
	rfc_transition_title_transition_resolve_R : '重新提交实施',
	rfc_transition_title_transition_re_implementing :'重新提交实施',
	rfc_transition_title_rfc_vital : '转化为重大变更',
	rfc_transition_title_rfc_committee_pass : '同意变更',// 委员会同意
	rfc_transition_title_rfc_audit_reject : '否决变更',// 委员会否决
	rfc_transition_title_rfc_close : '关闭工单',
	transition_evalpass_rfa:'启动发布流程',
	transition_evalpass_checking:'提交评审',
	transition_evalpass_cmdb:'变更配置项',
	transition_rfa_checking:'完成发布并提交评审',
	
	countersign_processDeleted:'该流程已经被其他用户删除！',
	countersign_processEnd:'该流程已经被其他用户结束，需要重新刷新！',
	countersign_processNotInActivit:'流程已经完成当前活动，需要重新刷新！',
	countersign_haveBeDeleteUser:'该用户已经被其他用户删除，需要重新刷新！',
	countersign_DoneOrDeleted:'该用户已经完成处理或者被其他用户从列表中删除删除，需要重新刷新！',
	
	// ---------------资产过保归还设置--------------------
	assetRemind_input_laterDays:'请输入过保提醒天数',
	assetRemind_laterDays_not_null:'过保提醒天数不能为空',
	assetRemind_laterDays_long_1000:'过保提醒天数不能超过1000',
	assetRemind_input_returnDays:'请输入归还提醒天数',
	assetRemind_returnDays_not_null:'归还提醒天数不能为空',
	assetRemind_returnDays_long_10000:'归还提醒天数不能超过10000',
	assetRemind_input_mailSender:'请选择邮件接受者',
	assetRemind_mailSender_not_null:'邮件接收者不能为空',
	
	
	//-----------------------------cmdb--------------
	ciRelationType_typeName_input:'请输入CI关系类型名称',
	ciRelationType_typeName_not_null:'CI关系类型名称不为空',
	ciRelationType_typeName_long_10:'CI关系类型名称长度不超过10',
	ciRelationType_viewDetail_input:'请输入CI关系类型描述',
	ciRelationType_viewDetail_long_255:'CI关系类型名称长度不超过255'
	
}