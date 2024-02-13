<template>
  <div id="app">
    <!-- Navbar -->
    <div id="nav">
      <b-nav>
        <b-nav-item active>Active</b-nav-item>
        <b-nav-item>Link</b-nav-item>
        <b-nav-item>Another Link</b-nav-item>
        <b-nav-item disabled>Disabled</b-nav-item>
      </b-nav>
    </div>

    <b-container>
      <b-row>
        <div class="grid-container">
          <!-- Sidebar -->
          <b-col sm="3">
            <b-sidebar z-index="-1" shadow visible no-close-on-route-change no-header-close>
              <b-container fluid>
                <b-nav vertical class="pt-5">
                  <p>
                    La práctica más fea de todas, ahora entiendo por qué no me
                    gusta el front y del porque no me fui a diseño, que pena.
                  </p>
                  <b-nav-item>
                    <b-link :to="{ name: 'inicio' }"> Inicio </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'renta' }"> Renta </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'informacion' }"> Informacion </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'oferta' }"> Oferta </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'catalogo' }"> Catalogo </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'detalles' }"> Detalles </b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'formulario' }">Formulario</b-link>
                  </b-nav-item>
                  <b-nav-item>
                    <b-link :to="{ name: 'pelicula' }">Pelicula</b-link>
                  </b-nav-item>
                </b-nav>
              </b-container>
            </b-sidebar>
          </b-col>

          <!-- Body -->
          <b-col md="10" class="pl-3 mt-3">
            <div id="t" class="body-with-image">
              <div class="content">
                <p>
                  Esto está muy feo una disculpa, desde que llevo front en la
                  prepa no se me ha dado. Hasta me da pena entregar esto pero
                  joder lo intenté y ni así. Esto parece de 3 o de prepa perdon.
                </p>
                <img src="src\assets\gatitio.jpg" alt="Imagen" />
                <img src="src\assets\appa.png" alt="Imagen" />
              </div>
            </div>
          </b-col>
        </div>
      </b-row>
    </b-container>
    <b-container>
      <router-view></router-view>
    </b-container>
  </div>
</template>

<style scoped>
#t {
  margin: 10px;
}

#nav {
  background: #ae9089;
  max-width: 100%;
  padding: 10px;
  border-radius: 1px;
  position: relative;
}

.grid-container {
  display: grid;
  grid-template-columns: 2fr 10fr;
}

img {
  margin: 10px;
  width: 20%;
  height: auto;
}

.content {
  padding: 20px;
}

.btn-lg {
  margin-right: 10px;
}
</style>
