export function loadState(){
    try{
        const jsonState=localStorage.getItem('state');
        if(jsonState===null){
            return undefined;
        }
        return JSON.parse(jsonState);
    }catch(err){
        return undefined;
    }
}

export function saveState(state){
    try{
        const jsonState=JSON.stringify(state);
        localStorage.setItem('state',jsonState);
    }catch (err) {
        
    }
}