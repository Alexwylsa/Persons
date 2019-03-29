function getIndex(list, id){
    for (var i=0; i<list.length; i++){

        if(list[i].id===id){
            return i;
        }
    }

    return -1;
}

var userApi = Vue.resource('/users{/id}');

template:

    '<div>'+
    '<input type="user" placeholder="Write your username" v-model="user"/> '+
    '<input type="button" value="Save" @click="save"/> '+
    '</div>',

methods: {
    save: function () {

        var user = {user: this.user};

        if(this.id) {

            userApi.update({id: this.id}, user).then(result=> result.json().then(data=>{

                var index=getIndex(this.users, data.id);
            this.users.splice(index, 1, data);
            this.user = '' //обновление поля ввода при добавлении новых сообщений
            this.id = '' //обновление поля ввода при добавлении новых сообщений


        })
        )
        } else {

            userApi.save({}, user).then(result =>
            result.json().then(data => {



                this.users.push(data);
            this.user = '' //обновление поля ввода при добавлении новых сообщений


        })
        )
        }
    }
}

});
