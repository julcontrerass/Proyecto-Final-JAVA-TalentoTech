document.addEventListener("DOMContentLoaded", async () => {
  const API_URL = 'http://localhost:8080/api';

  // Cargar productos desde el backend
  try {
    const response = await fetch(`${API_URL}/productos`);
    const productos = await response.json();
    cargarProductos(productos);
  } catch (error) {
    console.error('Error al cargar productos:', error);
    alert('No se pudieron cargar los productos. Asegúrate de que el backend esté ejecutándose.');
  }

  function cargarProductos(data) {
    const productosContainer = document.getElementById("productos-container");
    productosContainer.innerHTML = "";

    data.forEach((producto) => {
      const nombre = producto.nombre || producto.title;
      const descripcion = producto.descripcion || producto.description;
      const precio = producto.precio || producto.price;
      const imagen = producto.imagen || producto.image;
      const stock = producto.stock || 0;

      const shortDescription = descripcion.split(" ").slice(0, 5).join(" ") + "...";

      productosContainer.innerHTML += `
        <div class="card" style="border: 1px solid #ddd; max-width: 300px;">
          <img src="${imagen}" class="card-img-top" alt="${nombre}" style="height: 250px; object-fit: cover;">
          <div class="card-body">
            <span class="badge bg-success mb-2">Envío gratis</span>
            <h5 class="card-title" style="color: #443d3d; font-size: 1.1rem;">${nombre}</h5>
            <p class="card-text short-description text-muted" style="font-size: 0.9rem;">${shortDescription}</p>
            <p class="card-text full-description text-muted" style="display: none; font-size: 0.9rem;">${descripcion}</p>
            <button class="btn btn-link p-0 text-decoration-none" onclick="toggleDescription(this)" style="font-size: 0.85rem;">Ver más</button>
            <p class="card-text mt-2"><strong style="color: #294b92; font-size: 1.3rem;">$${precio.toFixed(2)}</strong></p>
            <p class="text-muted" style="font-size: 0.85rem;">Stock: ${stock} unidades</p>
            <button class="btn w-100" onclick="addToCart(${
              producto.id
            }, '${imagen}', '${nombre}', ${precio}, this)" style="background-color: #294b92; color: white;">Agregar al carrito</button>
          </div>
        </div>
      `;
    });
  }

  window.addToCart = function (id, image, title, price, button) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let existingProduct = cart.find((product) => product.id === id);
    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      cart.push({ id, image, title, price, quantity: 1 });
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartUI();

    // Cambiar el texto del botón
    button.textContent = "Agregado";
    button.disabled = true;
    setTimeout(() => {
      button.textContent = "Agregar al carrito";
      button.disabled = false;
    }, 1000);
  };

  window.toggleDescription = function (button) {
    const shortDescription = button.previousElementSibling;
    const fullDescription = shortDescription.nextElementSibling;
    if (fullDescription.style.display === "none") {
      fullDescription.style.display = "block";
      shortDescription.style.display = "none";
      button.textContent = "Ocultar descripción";
    } else {
      fullDescription.style.display = "none";
      shortDescription.style.display = "block";
      button.textContent = "Ver descripción";
    }
  };

  // FUNCIÓN UPDATECARTUI PARA MOSTRAR TARJETAS
  function updateCartUI() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const carritoItems = document.getElementById("carrito-items");
    carritoItems.innerHTML = "";
    let total = 0;

    if (cart.length === 0) {
      carritoItems.innerHTML =
        '<li class="text-center text-muted w-100">Tu carrito está vacío</li>';
      document.getElementById("realizar-compra").disabled = true;
    } else {
      document.getElementById("realizar-compra").disabled = false;

      cart.forEach((item) => {
        const cartItemHTML = `
          <li class="cart-item">
            <img src="${item.image}" alt="${item.title}">
            <div class="card-body">
              <h6 class="card-title">${item.title}</h6>
              <p class="card-text">Cantidad: <strong>${
                item.quantity
              }</strong></p>
              <p class="card-text">Precio unitario: ${item.price}</p>
              <p class="card-text">Subtotal: <strong>${(
                item.price * item.quantity
              ).toFixed(2)}</strong></p>
              <button class="btn btn-sm btn-danger" onclick="removeFromCart(${
                item.id
              })">
                Eliminar
              </button>
            </div>
          </li>
        `;
        carritoItems.innerHTML += cartItemHTML;
        total += item.price * item.quantity;
      });
    }

    document.getElementById("carrito-total").textContent = total.toFixed(2);
    document.getElementById("cart-counter").textContent = cart.reduce(
      (sum, item) => sum + item.quantity,
      0
    );
  }

  // FUNCIÓN PARA ELIMINAR PRODUCTOS INDIVIDUALES
  window.removeFromCart = function (id) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart = cart.filter((item) => item.id !== id);
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartUI();
  };

  document.getElementById("vaciar-carrito").addEventListener("click", () => {
    localStorage.clear();
    updateCartUI();
  });

  // FUNCIONALIDAD PARA REALIZAR COMPRA - CONECTADA CON BACKEND
  document.getElementById("realizar-compra").addEventListener("click", async () => {
    const API_URL = 'http://localhost:8080/api';
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) {
      alert("Tu carrito está vacío");
      return;
    }

    const productos = {};
    cart.forEach(item => {
      productos[item.id] = item.quantity;
    });

    try {
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

      const pedido = await response.json();
      document.getElementById("modal-total").textContent = pedido.total.toFixed(2);

      const modal = new bootstrap.Modal(document.getElementById("compraExitosaModal"));
      modal.show();

      localStorage.clear();
      updateCartUI();
    } catch (error) {
      alert(error.message);
    }
  });

  updateCartUI();
});
