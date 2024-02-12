<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
    </b-container>
    ESTAS EN OFERTA DE ESPACIO
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          text: "Inicio",
          to: { name: "inicio" },
        },
        {
          text: "Renta",
          to: { name: "renta" },
        },
        {
          text: "Informacion",
          to: { name: "informacion" },
        },
        {
          text: "Oferta",
          active: true,
        },
      ],
    };
  },
};
</script>

<style></style>
