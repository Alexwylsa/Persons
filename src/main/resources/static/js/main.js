// function getIndex(list, id){
//     for (var i=0; i<list.length; i++){
//
//         if(list[i].id===id){
//             return i;
//         }
//     }
//
//     return -1;
// }
//
//
// var userApi = Vue.resource('/users{/id}');
//
//
// <!-- Добавление сообщений -->
//
// Vue.component('username-form', {
//     props: ['users', 'userAttr'],
// data: function () {
//
//     return {
//
//         username: '',
//         password: '',
//         id: ''
//
//     }
//
//     },
//         watch: {
//
//         userAttr:function(newVal, oldVal) {
//
//             this.username=newVal.username;
//             this.password=newVal.password;
//             this.id= newVal.id;
//         }
//
//         },
//
//
//
//
//
//
//
//
//
//     template:
//
//         '<div>'+
//         '<input type="username" placeholder="Write username" v-model="username"/> '+
//         '<input type="password" placeholder="Write password" v-model="password"/> '+
//         '<input type="button" value="Save" @click="save"/> '+
//         '</div>',
//
//     methods: {
//     save: function () {
//
//         var username = {username: this.username};
//         var password = {password: this.password};
//
//         if(this.id) {
//
//             userApi.update({id: this.id}, username).then(result=> result.json().then(data=>{
//
//                 var index=getIndex(this.users, data.id);
//                 this.users.splice(index, 1, data);
//             this.username = '' //обновление поля ввода при добавлении новых сообщений
//             this.id = '' //обновление поля ввода при добавлении новых сообщений
//             this.password = '' //обновление поля ввода при добавлении новых сообщений
//
//
//             })
//         )
//         } else {
//
//             userApi.save({}, username, password).then(result =>
//             result.json().then(data => {this.users.push(data);
//             this.username = '' //обновление поля ввода при добавлении новых сообщений
//             this.password = '' //обновление поля ввода при добавлении новых сообщений
//
//
//         })
//         )
//         }
//     }
//     }
//
// });
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// <!-- Отображение сообщений -->
// Vue.component('username-row', {
//
//     props: ['username', 'editMethod', 'users'],
//
//     template: '<div>'+
//         '<i>({{ username.id }})</i>{{ username.username }}' +
//         '<span style="position: absolute; right: 0">' +
//
//         '<input type="button" value="Edit" @click="edit"/>'+ // кнопка редактироания сообщений
//         '<input type="button" value="X" @click="del"/>'+ // кнопка удаления сообщений
//         '</span>'+
//         '</div>',
//
//     methods: {
//
//         edit: function () {
//
//             this.editMethod(this.username);
//
//
//
//         },
//
//         del: function () {
//
//             userApi.remove({id: this.username.id}).then(result=> {
//
//                 if(result.ok){
//
//                     this.users.splice(this.users.indexOf(this.username), 1)
//                              }
//
//             })
//
//         }
//
//
//     }
//
// });
//
// Vue.component('users-list', {
//     props:['users'],
//     data: function(){
//         return {
//             username: null
//         }
//     },
//     template:
//         '<div style="position: relative; width: 300px;">'+
//         '<username-form :users="users" :userAttr="username"/>'+
//         '<username-row v-for="username in users" :key="username.id" :username="username"'+
//         ':editMethod="editMethod" :users="users"/>'+
//         '</div>',
//     created: function () {
//         userApi.get().then(result =>
//     result.json().then(data =>
//     data.forEach(username => this.users.push(username))
//     ))
//
//     },
//
//     methods: {
//
//         editMethod: function(username) {
//
//             this.username=username;
//
//         }
//
//     }
// });
//
//
//
// var app = new Vue({
//
//     el: '#app',
//     template: '<users-list :users="users" />',
//     data: {
//         users: []
//     }
// });
