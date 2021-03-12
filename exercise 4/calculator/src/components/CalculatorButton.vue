<template>
     <button @click="clicked">{{sign}}</button>
</template>

<script>
  import {defineComponent} from 'vue';
export default defineComponent ( {
  name: 'CalculatorButton',
    props: ['sign'],
    setup(props, { emit }){
        const clicked = () =>{
            emit("clicked", props.sign)
        }
    return {clicked}
    }
    
})
</script>

<style scoped>

    button {
    margin: 0 auto;
    display:inline-block;
    padding:0.35em 1.2em;
    border:0.1em solid #FFFFFF;
    border-radius:0.12em;
    box-sizing: border-box;
    text-decoration:none;
    font-weight:300;
    color: black;
    text-align:center;
    transition: all 0.2s;
    width: 100%;
    height: 100%;
  }
  button::hover{
    background-color: #13171E;
  }
</style>
