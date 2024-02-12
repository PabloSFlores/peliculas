<template>
  <div id="app">
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <!--NOMBRE-->
      <b-form-group id="nameForm" label="Nombre:" label-for="nombre">
        <b-form-input
          id="nombre"
          label="Nombre"
          v-model="form.nombre"
          type="text"
          required
          placeholder="Nombre"
        >
        </b-form-input>

        <b-form-input
          id="apellidoP"
          label="Apellido Paterno"
          v-model="form.apellidoP"
          type="text"
          required
          placeholder="Apellido Paterno"
        >
        </b-form-input>

        <b-form-input
          id="apellidoM"
          label="Apellido Materno"
          v-model="form.apellidoM"
          placeholder="Apellido Materno"
        ></b-form-input>
      </b-form-group>

      <!--DIRECCION-->
      <b-form-group id="direccionForm" label="Direccion" label-for="direccion">
        <b-form-input
          id="cp"
          label="Codipo Postal"
          v-model="form.codigoPostal"
          type="number"
          required
          maxlength="5"
          minlenght="5"
          placeholder="Codigo Postal"
        >
        </b-form-input>

        <b-form-input
          id="calle"
          label="Calle"
          v-model="form.calle"
          type="text"
          required
          placeholder="Avda. Constitución"
        >
        </b-form-input>

        <b-form-input
          id="numero"
          label="Número"
          v-model="form.numero"
          type="number"
          required
          placeholder="#123"
        >
        </b-form-input>

        <b-form-input
          id="ciudad"
          label="Ciudad"
          v-model="form.ciudad"
          type="text"
          required
          placeholder="Ciudad Celeste"
        >
        </b-form-input>
      </b-form-group>

      <!--FECHNAC-->
      <b-form-group
        id="feachaNacForm"
        label="Fecha de nacimiento"
        label-for="fecha nacimiento"
      >
        <b-form-input id="fechaNac" v-model="form.fecha" type="date" required>
        </b-form-input>
      </b-form-group>

      <!--EMAIL-->
      <b-form-group id="emailForm" label="Email:" label-for="email">
        <b-form-input
          id="email"
          v-model="form.email"
          type="email"
          required
          placeholder="correo@correo.com"
        >
        </b-form-input>
      </b-form-group>

      <!--NUMTELE-->
      <b-form-group
        id="telefonoForm"
        label="Núm Telefónico"
        label-for="núm telefónico"
      >
        <b-form-input
          id="telefono"
          v-model="form.telefono"
          type="tel"
          required
          maxlength="10"
          placeholder="0123456789"
        >
        </b-form-input>
      </b-form-group>

      <!--FOTO-->
      <div>
        <label>Fotografia</label>
        <input
          type="file"
          @change="handleFileUpload"
          accept="image/png"
          required
        />
      </div>
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        nombre: "",
        apellidoP: "",
        apellidoM: "",
        codigoPostal: "",
        calle: "",
        numero: "",
        ciudad: "",
        fechaNac: "",
        email: "",
        telefono: "",
        foto: null,
      },
      show: true,
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      // Validar fecha de nacimiento
      const fechaNacimiento = new Date(this.fechaNacimiento);
          const edad = new Date().getFullYear() - fechaNacimiento.getFullYear();
          if (edad < 18) {
            alert('Debe tener al menos 18 años.');
            return;
          }

      alert(JSON.stringify(this.form));
    },
    onReset(event) {
      event.preventDefault();
      // Reiniciar inputs
      (this.form.nombre = ""),
        (this.form.apellidoP = ""),
        (this.form.apellidoM = ""),
        (this.form.codigoPostal = ""),
        (this.form.calle = ""),
        (this.form.numero = ""),
        (this.form.ciudad = ""),
        (this.form.fechaNac = ""),
        (this.form.email = ""),
        (this.form.telefono = ""),
        (this.form.foto.name = "");

      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>

<style></style>
