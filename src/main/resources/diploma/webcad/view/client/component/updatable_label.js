window.diploma_webcad_view_client_component_UpdatableLabel = function() {

    var e = this.getElement();

    e.innerHTML = "";
    
    this.onStateChange = function() {
        e.innerHTML = this.getState().text; 
    };
 
};