/* Korean initialisation for the jQuery calendar extension. */
/* Written by DaeKwon Kang (ncrash.dk@gmail.com), Edited by Genie and Myeongjin Lee. */
( function( factory ) {
	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define( [ "../widgets/datepicker" ], factory );
	} else {

		// Browser globals
		factory( jQuery.datepicker );
	}
}( function( datepicker ) {

datepicker.regional.ko = {
	dateFormat : 'yy-mm-dd',
	autoSize  : false,
	showAnim : 'fadeIn',
	changeYear : true, 
	changeMonth  : true,
	showOtherMonths : true,
	shwoOn : 'both',
	prevText: '이전 달',	// 마우스 오버시 이전달 텍스트
	nextText: '다음 달',	// 마우스 오버시 다음달 텍스트
	closeText: '닫기', // 닫기 버튼 텍스트 변경
	currentText: '오늘', // 오늘 텍스트 변경
	monthNames: [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12' ],
	monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],	//한글 캘린더 중 월 표시를 위한 부분
	dayNames: ['일', '월', '화', '수', '목', '금', '토'],	//한글 캘린더 요일 표시 부분
	dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],	//한글 요일 표시 부분
	dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],	// 한글 요일 표시 부분
	showMonthAfterYear: true,	// true : 년 월  false : 월 년 순으로 보여줌
	//showButtonPanel: true,	// 오늘로 가는 버튼과 달력 닫기 버튼 보기 옵션     
	onSelect: function(dateText, inst) { // 시작일 선택 시 종료일 같게
			$("#edate").val(dateText);
		}
	};
datepicker.setDefaults( datepicker.regional.ko );

return datepicker.regional.ko;

} ) );
