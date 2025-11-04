package segundum.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import segundum.modelo.EstadoProducto;
import segundum.modelo.LugarDeRecogida;
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

    @Override
    public void incrementarVisualizaciones(String idProducto) throws RepositorioException, EntidadNoEncontrada {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            Producto p = em.find(Producto.class, idProducto);
            if (p == null) {
                throw new EntidadNoEncontrada(idProducto + " no existe en el repositorio");
            }

            p.incrementarVisualizaciones();
            em.merge(p);

            em.getTransaction().commit();
        } catch (EntidadNoEncontrada e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RepositorioException("Error incrementando visualizaciones producto " + idProducto, e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public void modificarDatosProducto(String idProducto, Double nuevoPrecio, String nuevaDescripcion)
            throws RepositorioException, EntidadNoEncontrada {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            Producto p = em.find(Producto.class, idProducto);
            if (p == null) {
                throw new EntidadNoEncontrada(idProducto + " no existe en el repositorio");
            }

            boolean changed = false;
            if (nuevoPrecio != null) {
                p.setPrecio(nuevoPrecio);
                changed = true;
            }

            if (nuevaDescripcion != null) {
                p.setDescripcion(nuevaDescripcion);
                changed = true;
            }

            if (changed) {
                em.merge(p);
            }

            em.getTransaction().commit();
        } catch (EntidadNoEncontrada e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RepositorioException("Error modificando datos del producto " + idProducto, e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public void asignarLugarRecogida(String idProducto, double longitud, double latitud, String descripcionLugar)
            throws RepositorioException, EntidadNoEncontrada {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            Producto p = em.find(Producto.class, idProducto);
            if (p == null) {
                throw new EntidadNoEncontrada(idProducto + " no existe en el repositorio");
            }

            LugarDeRecogida lugar = p.getRecogida();

            if (lugar == null) {
                lugar = new LugarDeRecogida();
                lugar.setDescripcion(descripcionLugar);
                lugar.setLongitud(longitud);
                lugar.setLatitud(latitud);
                lugar.setProducto(p);
                em.persist(lugar);
                p.setRecogida(lugar);
                em.merge(p);
            } else {
                lugar.setDescripcion(descripcionLugar);
                lugar.setLongitud(longitud);
                lugar.setLatitud(latitud);
                em.merge(lugar);
            }

            em.getTransaction().commit();
        } catch (EntidadNoEncontrada e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RepositorioException("Error asignando lugar de recogida al producto " + idProducto, e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

}
