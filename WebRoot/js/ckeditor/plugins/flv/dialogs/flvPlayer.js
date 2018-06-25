CKEDITOR.dialog
		.add(
				'flv',
				function(a) {
					var b = a.config;
					varescape =function(value){
returnvalue;
};
return{
						title : '插入Flv视频',
						resizable : CKEDITOR.DIALOG_RESIZE_BOTH,
						minWidth : 350,
						minHeight : 300,
contents:[{
									id : 'info',
									label : '常规',
									accessKey : 'P',
									elements : [
											{
												type : 'hbox',
												widths : [ '80%', '20%' ],
												children : [ {
													id : 'src',
													type : 'text',
													label : '源文件'
												}, {
													type : 'button',
													id : 'browse',
													filebrowser : 'info:src',
													hidden : true,
													align : 'center',
													label : '浏览服务器'
												} ]
											},
											{
												type : 'hbox',
												widths : [ '35%', '35%', '30%' ],
												children : [
														{
															type : 'text',
															label : '视频宽度',
															id : 'mywidth',
															'default' : '470px',
															style : 'width:50px'
														},
														{
															type : 'text',
															label : '视频高度',
															id : 'myheight',
															'default' : '320px',
															style : 'width:50px'
														},
														{
															type : 'select',
															label : '自动播放',
															id : 'myloop',
															required : true,
															'default' : 'false',
															items : [
																	[ '是',
																			'true' ],
																	[ '否',
																			'false' ] ]
														} ]
											// children finish
											},
											{
												type : 'textarea',
												style : 'width:300px;height:220px',
												label : '预览',
												id : 'code'
											} ]
								}, {
									id : 'Upload',
									hidden : true,
									filebrowser : 'uploadButton',
									label : '上传',
									elements : [ {
										type : 'file',
										id : 'upload',
										label : '上传',
										size : 38
									}, {
										type : 'fileButton',
										id : 'uploadButton',
										label : '发送到服务器',
										filebrowser : 'info:src',
										'for' : [ 'Upload', 'upload' ]
									// 'page_id', 'element_id'
									} ]
								} ],
						onOk : function() {
							mywidth = this.getValueOf('info', 'mywidth');
							myheight = this.getValueOf('info', 'myheight');
							myloop = this.getValueOf('info', 'myloop');
							mysrc = this.getValueOf('info', 'src');
							html = '' + escape(mysrc) + '';
							// editor.insertHtml("<preclass=\"brush:"+lang+";\">"+html+"</pre>");
							a
									.insertHtml("<embed height="
											+ myheight
											+ " width="
											+ mywidth
											+ " autostart="
											+ myloop
											+ " flashvars=\"file="
											+ html
											+ "\" allowfullscreen=\"true\" allowscriptaccess=\"always\" bgcolor=\"#ffffff\" src=\""
											+ b.flv_path
											+ "jwplayer.swf\"></embed>");
						},
						onLoad : function() {
						}
					};
				});
