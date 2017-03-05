package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Medicacion;

@Repository
@Transactional
public class RepositorioMedicacionImpl extends RepositorioAbstractoImpl<Long, Medicacion>
		implements RepositorioMedicacion {

	@Override
	public Class<Medicacion> obtenClaseT() {
		return Medicacion.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "ENFERMEDAD_MEDICAMENTO";
	}

	@Override
	public List<Medicacion> deleteByDisease(Long idDisease) {
		List<Medicacion> entradas = listByDisease(idDisease);
		try {
			entityManager.createNativeQuery("DELETE FROM ENFERMEDAD_MEDICAMENTO WHERE id_enfermedad = ?")
					.setParameter(1, idDisease).executeUpdate();
		} catch (Exception e) {

		}
		return entradas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicacion> listByDisease(Long idDisease) {
		List<Medicacion> resultado;
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
