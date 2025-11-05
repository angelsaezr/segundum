package segundum.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import segundum.modelo.EstadoProducto;
import segundum.modelo.Producto;
import segundum.utils.EntityManagerHelper;

public class RepositorioProductosAdHocJPA extends RepositorioJPA<Producto> implements RepositorioProductosAdHoc {

    @Override
    public Class<Producto> getClase() {
        return Producto.class;
    }

    @Override
    public List<Producto> buscarProductos(String idCategoria, String texto, EstadoProducto estado, Double precioMax)
            throws RepositorioException {

        try {
            EntityManager em = EntityManagerHelper.getEntityManager();

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p FROM Producto p ");

            List<String> condiciones = new ArrayList<>();

            if (idCategoria != null && !idCategoria.trim().isEmpty()) {
                condiciones.add("p.categoria.id = :idCategoria");
            }

            if (texto != null && !texto.trim().isEmpty()) {
                condiciones.add("(LOWER(p.titulo) LIKE :texto OR LOWER(p.descripcion) LIKE :texto)");
            }

            if (estado != null) {
                condiciones.add("p.estado = :estado");
            }

            if (precioMax != null) {
                condiciones.add("p.precio <= :precioMax");
            }

            if (!condiciones.isEmpty()) {
                sb.append("WHERE ").append(String.join(" AND ", condiciones));
            }

            TypedQuery<Producto> query = em.createQuery(sb.toString(), Producto.class);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);

            if (idCategoria != null && !idCategoria.trim().isEmpty()) {
                query.setParameter("idCategoria", idCategoria);
            }

            if (texto != null && !texto.trim().isEmpty()) {
                String pattern = "%" + texto.toLowerCase() + "%";
                query.setParameter("texto", pattern);
            }

            if (estado != null) {
                query.setParameter("estado", estado);
            }

            if (precioMax != null) {
                query.setParameter("precioMax", precioMax);
            }

            return query.getResultList();

        } catch (RuntimeException e) {
            throw new RepositorioException("Error buscando productos", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

}
