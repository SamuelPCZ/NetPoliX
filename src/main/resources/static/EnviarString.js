const form = document.querySelector('form');
form.addEventListener('submit', function (event) {
    const select = document.getElementById('categorias');
    const selected = Array.from(select.selectedOptions).map(option => option.value);
    const selectedString = selected.join(',');
    // Añadir las categorías como un campo oculto
    document.getElementById('categoriasString').value = selectedString;
});

// script.js

// Arreglos para almacenar los actores, directores y productores
let directores = [];
let actores = [];
let productores = [];

function agregarDirector() {
    const directorInput = document.getElementById('directorInput');
    const listaDirectores = document.getElementById('directoresList');

    const directorNombre = directorInput.value.trim(); // Obtener el valor del input

    if (directorNombre) { // Comprobar que no esté vacío
        directores.push(directorNombre); // Agregar el nombre al arreglo
        const li = document.createElement('li');
        li.textContent = directorNombre; // Crear un nuevo elemento de lista con el nombre del director
        listaDirectores.appendChild(li); // Agregar el nuevo elemento a la lista
        directorInput.value = ''; // Limpiar el input
        actualizarDirectoresString(); // Actualizar el input oculto con la cadena de directores
    } else {
        alert('Por favor ingrese un nombre'); // Mensaje si el input está vacío
    }
}

function agregarActor() {
    const actorInput = document.getElementById('actorInput');
    const listaActores = document.getElementById('actoresList');

    const actorNombre = actorInput.value.trim(); // Obtener el valor del input

    if (actorNombre) { // Comprobar que no esté vacío
        actores.push(actorNombre); // Agregar el nombre al arreglo
        const li = document.createElement('li');
        li.textContent = actorNombre; // Crear un nuevo elemento de lista con el nombre del actor
        listaActores.appendChild(li); // Agregar el nuevo elemento a la lista
        actorInput.value = ''; // Limpiar el input
        actualizarActoresString(); // Actualizar el input oculto con la cadena de actores
    } else {
        alert('Por favor ingrese un nombre'); // Mensaje si el input está vacío
    }
}

function agregarProductor() {
    const productorInput = document.getElementById('productorInput');
    const listaProductores = document.getElementById('productoresList');

    const productorNombre = productorInput.value.trim(); // Obtener el valor del input

    if (productorNombre) { // Comprobar que no esté vacío
        productores.push(productorNombre); // Agregar el nombre al arreglo
        const li = document.createElement('li');
        li.textContent = productorNombre; // Crear un nuevo elemento de lista con el nombre del productor
        listaProductores.appendChild(li); // Agregar el nuevo elemento a la lista
        productorInput.value = ''; // Limpiar el input
        actualizarProductoresString(); // Actualizar el input oculto con la cadena de productores
    } else {
        alert('Por favor ingrese un nombre'); // Mensaje si el input está vacío
    }
}

// Funciones para actualizar las cadenas de los inputs ocultos
function actualizarDirectoresString() {
    const directoresString = document.getElementById('directoresString');
    directoresString.value = directores.join(', '); // Actualizar el valor del input oculto
}

function actualizarActoresString() {
    const actoresString = document.getElementById('actoresString');
    actoresString.value = actores.join(', '); // Actualizar el valor del input oculto
}

function actualizarProductoresString() {
    const productoresString = document.getElementById('productoresString');
    productoresString.value = productores.join(', '); // Actualizar el valor del input oculto
}
