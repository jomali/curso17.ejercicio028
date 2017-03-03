package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

@Repository
@Transactional
public class RepositorioMedicamentoImpl extends RepositorioAbstractoImpl<Long, Medicamento>
		implements RepositorioMedicamento {

	@Override
	public Class<Medicamento> obtenClaseT() {
		return Medicamento.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "MEDICAMENTO";
	}

	@Override
	public List<Medicamento> listByType(Long idType) {
		return entityManager
				.createNamedQuery("SELECT * FROM MEDICAMENTO WHERE id_tipo_medicamento = ?", Medicamento.class)
				.setParameter(1, idType).getResultList();

	}

}
