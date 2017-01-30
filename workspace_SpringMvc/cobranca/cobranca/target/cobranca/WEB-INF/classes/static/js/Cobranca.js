$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event){
	var button = $(event.relatedTarget);//captura o botão que desparou o evento
	
	var codigoTitulo = button.data('codigo');//acessa os atributos 'attr' do botão
	var descricaoTitulo = button.data('descricao');//acessa os atributos 'attr' do botão
	
	var modal = $(this);//pega o modal
	var form = modal.find('form');//usa o find para encontrar o componente form
	var action = form.data('url-base');//pega o atributo actio através do attr
	
	if(!action.endsWith('/')){//se a url não terminar com barra, ele adiciona a barra e concatena com o código
		action += '/';
	}
	form.attr('action',action + codigoTitulo);//altera o action do form
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>'+descricaoTitulo+'</strong>?');
	//adiciona uma mensagem customizada ao formulário 
});

$(function(){
	//TODO: sempre que carregar a página vai procurar os sequintes elementos
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal:',',thousands:'.',allowZero:true});
	$('.js-atualizar-status').on('click',function(event){
		event.preventDefault();
		
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		
		var response = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		
		response.done(function(e){
			var codigoTitulo = botaoReceber.data('codigo');
			$('[data-role='+codigoTitulo+']').html('<span class="label label-success">'+e+'</span>');
			botaoReceber.hide();
		});
		
		response.fail(function(e){
			console.log(e);
			alert('Erro recebendo cobrança');
		});
	});
});








