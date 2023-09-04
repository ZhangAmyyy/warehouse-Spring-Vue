import vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter}  from "@/router/MyTemp";
// import router from "@/router/MyTemp";
vue.use(Vuex)

function addNewRoute(menuList) {
    console.log(menuList)
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if(routeItem.path=="/MyTemp" && menuList!=undefined){
            console.log('2222')
            menuList.forEach(menu=>{
                let childRoute =  {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })
    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state: {
        menu: [],
    },
    mutations: {
        setMenu(state,menuList) {
            state.menu = menuList
            console.log('1111'+menuList)
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu
        }
    }
})