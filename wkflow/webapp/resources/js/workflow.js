/**
 * 
 */

/* Função para layout responsivo - início */
$(function() {
	$('.js-toggle').bind('click', function(event) {
		$('.js-sidebar, .js-content').toggleClass('is-toggled');
		event.preventDefault();
	});	
});
/* Função para layout responsivo - fim */


function activeLabelColor(e) {
	$("label[for='"+$(e).attr('id')+"']").css("color", "#03a9f4");
}

function desactiveLabelColor(e) {
	$("label[for='"+$(e).attr('id')+"']").css("color", "#9e9e9e");
}


function activeLabel(e) {
	
//	var elementx = ($(document.getElementById(e)));
	
//	$('label[for="'+e+'"]').addClass("active");
		
	$("label[for='"+$(e).attr('id')+"']").addClass('active')
		

}


function removeCaledarLabel() {

	  var input_selector = 'input[type=text], text';
	  $(input_selector).each(function(index, element) {
	    if ($(element).val().length == 0 && ($(element).hasClass("hasDatepicker") ) ) {
	    	
	    	$("label[for='"+$(element).attr('id')+"']").removeClass('active')
	    }
	  });

		
//	$("label[for='"+$(e).attr('id')+"']").addClass('active')
		

}
		


function WfUpdateFields (){
	  var input_selector = 'input[type=text], input[type=password], input[type=email], input[type=url], input[type=tel], input[type=number], input[type=search], textarea, text';
	  $(input_selector).each(function(index, element) {
	    if ($(element).val().length > 0 || element.autofocus ||$(this).attr('placeholder') !== undefined || $(element)[0].validity.badInput === true) {

//	    	if( $(this).hasClass("hasDatepicker") ){
	    	if( $(this).parent().hasClass("hasDatepicker") ){
	    		$(this).blur();
	    		$("label[for='"+$(this).attr('id')+"']").addClass('active')
	    		$("label[for='"+$(this).attr('id')+"']").css("color","")
	    		$("label[for='"+$(this).attr('id')+"']").css("color",$(this).css("color"))
	    	}
	    	
	      $(this).siblings('label, i').addClass('active');
	    }
	    else {
	      $(this).siblings('label, i').removeClass('active');
	      
	    	if( $(this).parent().hasClass("hasDatepicker") ){
	    		$(this).blur();
	    		$("label[for='"+$(this).attr('id')+"']").css("color","")
	    		$("label[for='"+$(this).attr('id')+"']").css("color",$(this).css("color"))
	    	}
	      
	    }
	  });

};

/*
function WfUpdateFields (){
	  var input_selector = 'input[type=text], input[type=password], input[type=email], input[type=url], input[type=tel], input[type=number], input[type=search], textarea, text';
	  $(input_selector).each(function(index, element) {
	    if ($(element).val().length > 0 || element.autofocus ||$(this).attr('placeholder') !== undefined || $(element)[0].validity.badInput === true) {

	    	if( $(this).hasClass("hasDatepicker") ){
	    		$(this).blur();
	    		$("label[for='"+$(this).attr('id')+"']").addClass('active')
	    	}
	    	
	      $(this).siblings('label, i').addClass('active');
	    }
	    else {
	      $(this).siblings('label, i').removeClass('active');
	      
	      if( $(this).hasClass("hasDatepicker") ){
	    	  $("label[for='"+$(this).attr('id')+"']").removeClass('active')
	      }
	      
	      
	    }
	  });

};
*/
PrimeFaces.locales['pt_BR'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Próximo',
        currentText: 'Começo',
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Só Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'Mês',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };


