package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.JornadaTransporte;

public class JornadaTransporteRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	EntityManager manager;

	public JornadaTransporteRepository(EntityManager manager) {
		this.manager = manager;
	}

	public JornadaTransporteRepository() {

	}

	public JornadaTransporte porId(Long id) {
		return this.manager.find(JornadaTransporte.class, id);
	}

	public List<JornadaTransporte> todasJornadasTransporte() {
		TypedQuery<JornadaTransporte> query = manager.createQuery(
				"from JornadaTransporte", JornadaTransporte.class);
		return query.getResultList();
	}

	public void salvar(JornadaTransporte jornadaTransporte) {
		this.manager.persist(jornadaTransporte);
	}

	public void excluir(JornadaTransporte jornadaTransporte) {
		this.manager.remove(jornadaTransporte);
	}

	public void editar(JornadaTransporte jornadaTransporte) {
		this.manager.merge(jornadaTransporte);
	}
}
