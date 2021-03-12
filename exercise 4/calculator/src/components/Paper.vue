<template>
     <div>
        <slot/>
     </div>
</template>

<script>
  import {defineComponent} from 'vue';
export default defineComponent ( {
  name: 'Paper',
})
</script>

<style scoped>
    div{
        padding: 25px;
        border-radius: 5px;
        box-shadow: 5px 5px 5px 5px rgba(0,0,0,0.75);
    }
</style>
