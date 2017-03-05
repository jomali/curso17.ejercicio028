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

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicamento> listByType(Long idType) {
		List<Medicamento> resultado;
		try {
			resultado = entityManager
					.createNativeQuery("SELECT * FROM MEDICAMENTO WHERE id_tipo_medicamento = ?", obtenClaseT())
					.setParameter(1, idType).getResultList();
		} catch (Exception e) {
			return null;
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicamento> listByDisease(Long idDisease) {
		List<Medicamento> resultado;
		try {
			resultado = entityManager.createNativeQuery(
					"SELECT MEDICAMENTO.* FROM MEDICAMENTO INNER JOIN ENFERMEDAD_MEDICAMENTO ON MEDICAMENTO.id = ENFERMEDAD_MEDICAMENTO.id_medicamento AND ENFERMEDAD_MEDICAMENTO.id_enfermedad = ?",
					obtenClaseT()).setParameter(1, idDisease).getResultList();
		} catch (Exception e) {
			return null;
		}
		return resultado;
	}

}
