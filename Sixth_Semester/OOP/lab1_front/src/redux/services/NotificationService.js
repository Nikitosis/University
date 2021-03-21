import Noty from "noty";

export function showSuccessNotification(text){
    new Noty({
        type:"success",
        theme:"relax",
        layout:"topRight",
        text:text,
        timeout:2000
    }).show();
}

export function showErrorNotification(text){
    new Noty({
        type:"error",
        theme:"relax",
        layout:"topRight",
        text:text,
        timeout:2000
    }).show();
}