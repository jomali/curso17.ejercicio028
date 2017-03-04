package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.EnfermedadMedicamento;

@Repository
@Transactional
public class RepositorioEnfermedadMedicamentoImpl extends RepositorioAbstractoImpl<Long, EnfermedadMedicamento>
		implements RepositorioEnfermedadMedicamento {

	@Override
	public Class<EnfermedadMedicamento> obtenClaseT() {
		return EnfermedadMedicamento.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "ENFERMEDAD_MEDICAMENTO";
	}

	@Override
	public List<EnfermedadMedicamento> deleteByDisease(Long idDisease) {
		List<EnfermedadMedicamento> entradas = listByDisease(idDisease);
		entityManager.createNativeQuery("DELETE FROM ENFERMEDAD_MEDICAMENTO WHERE id_enfermedad = ?").setParameter(1,  idDisease).executeUpdate();
		return entradas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnfermedadMedicamento> listByDisease(Long idDisease) {
		List<EnfermedadMedicamento> resultado;
		try {
			resultado = entityManager
					.createNativeQuery("SELECT * FROM ENFERMEDAD_MEDICAMENTO WHERE id_enfermedad = ?", obtenClaseT())
					.setParameter(1, idDisease).getResultList();
		} catch (Exception e) {
			return null;
		}
		return resultado;
	}

}
