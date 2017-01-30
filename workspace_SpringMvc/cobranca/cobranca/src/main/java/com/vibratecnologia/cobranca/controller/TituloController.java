package com.vibratecnologia.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vibratecnologia.cobranca.model.StatusTitulo;
import com.vibratecnologia.cobranca.model.Titulo;
import com.vibratecnologia.cobranca.repository.Titulos;
import com.vibratecnologia.cobranca.repository.filter.TituloFilter;
import com.vibratecnologia.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	//@Autowired
	//private Titulos titulos;
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		//mv.addObject("todosStatusTitulo", StatusTitulo.values());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes){
		//TODO: Salvar no banco de dados
		if(errors.hasErrors()){//Se houver erro de validação ele retorna para a mesma página
			return CADASTRO_VIEW;
		}
		try{
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso.");
			return "redirect:/titulos/novo";
		}catch(IllegalArgumentException e){
			errors.rejectValue("dataVencimento,dataPagamento", null,e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro")TituloFilter filtro){//O @ModelAttribute permite passar um parametro que faz com quê o valor em um campo não desapareça durante a busca
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		//String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		//List<Titulo> todosTitulos = titulos.findByDescricaoContaining(descricao);
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	@RequestMapping("{codigo}")//Para receber o codigo do recurso a ser editado, porque o codigo pode variar.
	public ModelAndView edicao(@PathVariable("codigo")Titulo titulo){
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value = "{codigo}",method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo,RedirectAttributes attributes){
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso.");
		return "redirect:/titulos";
	}
	
	@RequestMapping(value = "/{codigo}/receber",method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){//@ResponseBody retorna o valor direto (string) ao invés de retornar uma View
		return cadastroTituloService.receber(codigo);
		
	}
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo>todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
}
