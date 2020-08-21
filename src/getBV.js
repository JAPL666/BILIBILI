let url=window.location.href;

if(url.indexOf("/favlist?fid=")!==-1){
    Create(1);
}else if(url.indexOf("/video")!==-1||url.indexOf("/channel/detail?cid=")!==-1){
    Create(2);
}
function Create(x){
    let btn=document.createElement("button");
    btn.innerText="一键获取BV号";
    btn.style="height:50px; width:130px; position: fixed; left:0px; top:60px;  z-index: 100;";
    btn.onclick=function () {
        let doc=document.body.getElementsByTagName("li");
        let bvs="";
        for (let i=0;i<doc.length;i++){

            let className="";
            if(x===1){
                className="small-item";
            }else if (x===2){
                className="small-item fakeDanmu-item";
            }

            if(doc[i].className===className){
                let bv=doc[i].attributes.getNamedItem("data-aid").value;
                bvs+=bv+";";
                //let a=doc[i].getElementsByClassName("title")[0].textContent;
                //alert(a);
            }
        }
        //复制
        let transfer = document.createElement('input');
        document.body.appendChild(transfer);
        transfer.value = bvs;// 这里表示想要复制的内容
        transfer.focus();
        transfer.select();
        if (document.execCommand('copy')) {
            document.execCommand('copy');
        }
        transfer.blur();
        document.body.removeChild(transfer);
        alert("复制成功！");
    }
    document.body.appendChild(btn);
}