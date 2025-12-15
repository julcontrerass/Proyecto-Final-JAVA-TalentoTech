const API_URL = 'http://localhost:8080/api';

async function obtenerProductos() {
  const response = await fetch(`${API_URL}/productos`);
  return await response.json();
}

async function crearPedido(carrito) {
  const productos = {};
  carrito.forEach(item => {
    productos[item.id] = item.quantity;
  });

  const response = await fetch(`${API_URL}/pedidos`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productos })
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.mensaje || 'Error al crear el pedido');
  }

  return await response.json();
}

document.addEventListener("DOMContentLoaded", async () => {
  const productos = await obtenerProductos();
  cargarProductos(productos);

  document.getElementById("realizar-compra").addEventListener("click", async () => {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) {
      alert("Tu carrito está vacío");
      return;
    }

    try {
      const pedido = await crearPedido(cart);
      const total = pedido.total;
      document.getElementById("modal-total").textContent = total.toFixed(2);

      const modal = new bootstrap.Modal(document.getElementById("compraExitosaModal"));
      modal.show();

      localStorage.clear();
      updateCartUI();
    } catch (error) {
      alert(error.message);
    }
  });
});
