
import VueRouter from 'vue-router';
import LoginPage from "@/components/LoginPage";
import MyTemp from "@/components/MyTemp";
import AccountCenter from "@/components/AccountCenter";
// import AdminManage from "@/components/admin/AdminManagement";
// import UserManage from "@/components/user/UserManage";




const routes =[
    {
        path:'/',
        name:'login',
        component:LoginPage
    } ,
    {
        path:'/MyTemp',
        name:'MyTemp',
        component:MyTemp,
        children:[
            {
                path:'/AccountCenter',
                name:'AccountCenter',
                meta:{
                    title:'HomePage'
                },
                component:AccountCenter,
            },
            // {
            //     path:'/AdminManagement',
            //     name:'AdminManagement',
            //     meta:{
            //         title:'Administration'
            //     },
            //     component:AdminManage,
            // },
            // {
            //     path:'/UserManagement',
            //     name:'UserManage',
            //     meta:{
            //         title:'UserManage'
            //     },
            //     component:UserManage,
            // },
        ]
    }


    ]
const router =  new VueRouter({
    mode:'history',
    routes })




const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

export function resetRouter() {
    router.matcher= new VueRouter({
        mode: 'history',
        routes: []
    }).matcher

    // router.options.routes=[]
}



export  default router;