// Seleccionar la base de datos
db = db.getSiblingDB('elfutbolconjavi');

// Crear colección "posts" si no existe
db.createCollection('posts');

// Insertar un documento inicial
db.posts.insertMany([
  {
    title: "¡Bienvenido al blog El Fútbol con Javi!",
    content: "Este es el primer post de prueba en MongoDB.",
    author: "Javi",
    date: new Date()
  },
  {
    title: "Análisis del último partido",
    content: "Aquí iría el análisis detallado del partido más reciente.",
    author: "Javi",
    date: new Date()
  }
]);
