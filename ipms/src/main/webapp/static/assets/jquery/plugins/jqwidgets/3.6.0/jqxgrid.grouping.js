/*
jQWidgets v3.6.0 (2014-Nov-25)
Copyright (c) 2011-2014 jQWidgets.
License: http://jqwidgets.com/license/
*/
define(["jquery"],function(jqxBaseFramework){
(function(a){a.jqx.dataview.grouping=function(){this.loadgrouprecords=function(E,H,s,F,I,f,R,G,x){var M=E;var j=this;var Q=new Array();for(var n=0;n<j.groups.length;n++){Q[n]=j.generatekey()}var K=new Array();var b=0;var Q=Q;var B=new Array();var P=H;var e=H;var C=j.groups.length;this.loadedrecords=new Array();this.bounditems=new Array();this.loadedrecords=new Array();this.loadedrootgroups=new Array();this.loadedgroups=new Array();this.loadedgroupsByKey=new Array();this.sortedgroups=new Array();var O=this.sortdata!=null;var S=O?this.sortdata:this.records;if(this.pageable){var A=Object.prototype.toString;var l=this.groups[0];Object.prototype.toString=(typeof l=="function")?l:function(){return this[l]};if(O){var c=this.sortfield;Object.prototype.toString=(typeof l=="function")?l:function(){return this[l]+" "+this[c]}}var u=new Array();var D=0;if(!S[H]){a.each(S,function(i,q){u[H+i++]=this});S=u}if(!O){S.sort(function(q,i){if(q===undefined){q=null}if(i===undefined){i=null}if(q===null&&i===null){return 0}if(q===null&&i!==null){return 1}if(q!==null&&i===null){return -1}q=q.toString();i=i.toString();if(a.jqx.dataFormat.isNumber(q)&&a.jqx.dataFormat.isNumber(i)){if(q<i){return -1}if(q>i){return 1}return 0}else{if(a.jqx.dataFormat.isDate(q)&&a.jqx.dataFormat.isDate(i)){if(q<i){return -1}if(q>i){return 1}return 0}else{if(!a.jqx.dataFormat.isNumber(q)&&!a.jqx.dataFormat.isNumber(i)){q=String(q).toLowerCase();i=String(i).toLowerCase()}}}try{if(q<i){return -1}if(q>i){return 1}}catch(T){var U=T}return 0})}else{S.sort(function(q,i){var q=q.value;var i=i.value;if(q===undefined){q=null}if(i===undefined){i=null}if(q===null&&i===null){return 0}if(q===null&&i!==null){return 1}if(q!==null&&i===null){return -1}if(a.jqx.dataFormat.isNumber(q)&&a.jqx.dataFormat.isNumber(i)){if(q<i){return -1}if(q>i){return 1}return 0}else{if(a.jqx.dataFormat.isDate(q)&&a.jqx.dataFormat.isDate(i)){if(q<i){return -1}if(q>i){return 1}return 0}else{if(!a.jqx.dataFormat.isNumber(q)&&!a.jqx.dataFormat.isNumber(i)){q=String(q).toLowerCase();i=String(i).toLowerCase()}}}try{if(q<i){return -1}if(q>i){return 1}}catch(T){var U=T}return 0})}Object.prototype.toString=A}if(this.virtualmode){var u=new Array();var D=0;for(var P=0;P<s-H;P++){u[H+D++]=S[P]}P=0;S=u}for(var d=H;d<s;d++){var y={};if(!O){y=a.extend({},S[d])}else{y=a.extend({},S[d].value)}id=y[j.uniqueId];if(I>=G||id!=R[I][j.uniqueId]||(f&&f[id])){x[x.length]=I}var L=new Array();var w=0;for(var n=0;n<C;n++){var t=j.groups[n];var J=y[t];if(J==null){J=""}L[w++]={value:J,hash:Q[n]}}if(L.length!=C){break}var k=null;var m="";var g=-1;for(var N=0;N<L.length;N++){g++;var p=L[N].value;var v=L[N].hash;m=m+"_"+v+"_"+p;if(B[m]!=undefined&&B[m]!=null){k=B[m];continue}if(k==null){k={group:p,subItems:new Array(),subGroups:new Array(),level:0};K[b++]=k;k.uniqueid=j.generatekey();j.loadedgroupsByKey[p]=k}else{var o={group:p,subItems:new Array(),subGroups:new Array(),parentItem:k,level:k.level+1};j.loadedgroupsByKey[k.uniqueid+"_"+p]=o;o.uniqueid=j.generatekey();k.subGroups[k.subGroups.length++]=o;k=o}B[m]=k}if(k!=null){if(undefined==y.uid){y.uid=this.getid(this.source.id,y,P)}if(!O){y.boundindex=P;this.recordsbyid["id"+y.uid]=S[d]}else{y.boundindex=S[d].index;this.recordsbyid["id"+y.uid]=S[d].value}this.bounditems[y.boundindex]=y;this.sortedgroups[P]=y;y.uniqueid=j.generatekey();y.parentItem=k;y.level=k.level+1;k.subItems[k.subItems.length++]=y}else{if(undefined==y.uid){y.uid=this.getid(this.source.id,y,P)}if(!O){y.boundindex=P;this.recordsbyid["id"+y.uid]=S[d]}else{y.boundindex=S[d].index;this.recordsbyid["id"+y.uid]=S[d].value}this.sortedgroups[P]=y;this.bounditems[y.boundindex]=y;y.uniqueid=j.generatekey()}I++;P++;e++}var h=function(q,T,U){for(var i=0;i<T.subItems.length;i++){T.subItems[i].visibleindex=E+U;q.rows[U]=T.subItems[i];q.loadedrecords[U]=T.subItems[i];U++}return U};var r=function(i,U,V){var q=function(Y){if(i.aggregates==true){var W=Y;var X={};if(W!=null){X.level=W.level;X.visibleindex=E+V;X.uniqueid=i.generatekey();i.rows[V]=X;i.loadedrecords[V++]=X;X.totalsrow=true;if(Y.subItems.length>0){W=Y.subItems[Y.subItems.length-1];X.parentItem=W.parentItem;if(X.parentItem.subItems){X.parentItem.subItems[X.parentItem.subItems.length]=X}}else{if(Y.subGroups.length>0){W=Y.subGroups[Y.subGroups.length-1];X.level=W.level;X.parentItem=Y;Y.subGroups[Y.subGroups.length]=X}}}}};for(subGroup in U.subGroups){var T=U.subGroups[subGroup];if(T.subGroups){i.loadedgroups[i.loadedgroups.length]=T;T.visibleindex=E+V;i.rows[V]=T;i.loadedrecords[V]=T;V++;if(T.subGroups.length>0){V=r(i,T,V)}else{if(T.subItems.length>0){V=h(i,T,V)}}q(T)}}if(U.subItems.length>0){V=h(i,U,V)}q(U);return V};var z=K.length;this.loadedgroups=new Array();this.rows=new Array();var M=0;for(var P=0;P<z;P++){var t=K[P];this.loadedrootgroups[P]=t;this.loadedgroups[this.loadedgroups.length]=t;t.visibleindex=E+M;this.rows[M]=t;this.loadedrecords[M]=t;M++;M=r(this,t,M)}return M};this._updategroupsinpage=function(C,r,K,d,u,j,f){var p=new Array();var w=[];if(this.groupable&&this.groups.length>0){var B=0;var h=new Array();var g=new Array();for(var m=0;m<C.groups.length;m++){g[m]=C.generatekey()}var F=0;var o=new Array();var k=0;if(f>this.totalrecords){f=this.totalrecords}for(var v=j;v<f;v++){var H=a.extend({},C.sortedgroups[v]);id=H[C.uniqueId];if(!C.pagesize||(K>=C.pagesize*C.pagenum&&K<C.pagesize*(C.pagenum+1))){if(d>=u||id!=p[d][C.uniqueId]||(updated&&updated[id])){w[w.length]=d}var e=new Array();var y=0;for(var m=0;m<C.groups.length;m++){var n=C.groups[m];var D=H[n];if(null==D){D=""}e[y++]={value:D,hash:g[m]}}if(e.length!=C.groups.length){break}var z=null;var s="";var c=-1;for(var A=0;A<e.length;A++){c++;var G=e[A].value;var l=e[A].hash;s=s+"_"+l+"_"+G;if(h[s]!=undefined&&h[s]!=null){z=h[s];continue}if(z==null){z={group:G,subItems:new Array(),subGroups:new Array(),level:0};o[k++]=z;var E=C.loadedgroupsByKey[G];if(E!=undefined){z.visibleindex=E.visibleindex;z.uniqueid=E.uniqueid}}else{var t={group:G,subItems:new Array(),subGroups:new Array(),parentItem:z,level:z.level+1};var E=C.loadedgroupsByKey[z.uniqueid+"_"+G];t.visibleindex=E.visibleindex;t.uniqueid=E.uniqueid;z.subGroups[z.subGroups.length++]=t;z=t}h[s]=z}if(z!=null){H.parentItem=z;H.level=z.level+1;z.subItems[z.subItems.length++]=H}d++}F++;K++}var x=function(L,M,q){for(var i=0;i<M.subItems.length;i++){p[q]=a.extend({},M.subItems[i]);q++}return q};var I=function(M){var q=false;for(subGroup in M.subGroups){var L=M.subGroups[subGroup];if(L.subGroups){if(L.subGroups.length>0){var i=I(L);if(i){q=true;return true}}if(L.subItems.length>0){q=true;return true}}}if(M.subItems.length>0){q=true;return true}return q};var b=function(q,M,i){for(subGroup in M.subGroups){var L=M.subGroups[subGroup];if(L.subGroups){if(I(L)){p[i]=L;i++;if(L.subGroups.length>0){i=b(q,L,i)}else{if(L.subItems.length>0){i=x(q,L,i)}}}}}if(M.subItems.length>0){i=x(q,M,i)}return i};var J=0;for(var F=0;F<o.length;F++){var n=o[F];if(I(n)){p[B]=n;B++;B=b(this,n,B)}}}return p}};a.extend(a.jqx._jqxGrid.prototype,{_initgroupsheader:function(){this.groupsheader.css("visibility","hidden");if(this._groupsheader()){this.groupsheader.css("visibility","inherit");var e=this;var c=this.gridlocalization.groupsheaderstring;this.groupsheaderdiv=this.groupsheaderdiv||a('<div style="width: 100%; position: relative;"></div>');this.groupsheaderdiv.height(this.groupsheaderheight);this.groupsheaderdiv.css("top",0);this.groupsheader.append(this.groupsheaderdiv);this.groupheadersbounds=new Array();var d=this.groups.length;this.groupsheaderdiv.children().remove();this.groupsheaderdiv[0].innerHTML="";var b=new Array();if(d>0){a.each(this.groups,function(i){var n=this;var m=e._getColumnText(this);var l=m.label;var k=e._rendergroupcolumn(l,n);k.addClass(e.toThemeProperty("jqx-grid-group-column"));e.groupsheaderdiv.append(k);if(e.closeablegroups){var j=a(k.find(".jqx-icon-close"));if(e.isTouchDevice()&&e.touchmode!==true){e.addHandler(j,"touchstart",function(){e.removegroupat(i);return false})}else{e.addHandler(j,"click",function(){e.removegroupat(i);return false})}}if(e.sortable){e.addHandler(k,"click",function(){var o=e.getcolumn(n);if(o!=null){e._togglesort(o)}return false})}b[b.length]=k;e._handlegroupstocolumnsdragdrop(this,k);if(i<d-1){var g=k.height();var h=a('<div style="float: left; position: relative;"></div>');if(e.rtl){h.css("float","right")}h.width(e.groupindentwidth/3);h.height(1);h.css("top",g/2);h.addClass(e.toThemeProperty("jqx-grid-group-column-line"));e.groupsheaderdiv.append(h)}})}else{var f=a('<div style="position: relative;">'+c+"</div>");this.groupsheaderdiv.append(f);if(this.rtl){f.addClass(this.toThemeProperty("jqx-rtl"))}}this._groupheaders=b;this._updategroupheadersbounds()}},_updategroupheadersbounds:function(){var c=this;var b=this.groupsheaderdiv.children().outerHeight();var d=(this.groupsheader.height()-b)/2;this.groupsheaderdiv.css("top",d);if(!this.rtl){this.groupsheaderdiv.css("left",d)}else{this.groupsheaderdiv.css("right",d)}if(this.rtl){this._groupheaders.reverse()}a.each(this._groupheaders,function(f){var e=this.coord();c.groupheadersbounds[f]={left:e.left,top:e.top,width:this.outerWidth(),height:this.outerHeight(),index:f}})},addgroup:function(c){if(c){var b=this;if(b.groups!==b.dataview.groups){b.dataview.groups=b.groups}b.groups[b.groups.length]=c;b.refreshgroups();this._raiseEvent(12,{type:"Add",index:b.groups[b.groups.length],groups:b.groups})}},insertgroup:function(d,c){if(d!=undefined&&d!=null&&d>=0&&d<=this.groups.length){if(c){var b=this;if(b.groups!==b.dataview.groups){b.dataview.groups=b.groups}b.groups.splice(d,0,c.toString());b.refreshgroups();this._raiseEvent(12,{type:"Insert",index:d,groups:b.groups})}}},refreshgroups:function(){this._refreshdataview();this._render(true,true,true,false)},_insertaftergroup:function(d,c){var b=this._getGroupIndexByDataField(d);this.insertgroup(b+1,c)},_insertbeforegroup:function(d,c){var b=this._getGroupIndexByDataField(d);this.insertgroup(b,c)},removegroupat:function(c){if(c>=0&&c!=null&&c!=undefined){var b=this;if(b.groups!==b.dataview.groups){b.dataview.groups=b.groups}b.groups.splice(c,1);b.refreshgroups();if(b.virtualmode){b.updatebounddata()}this._raiseEvent(12,{type:"Remove",index:c,groups:b.groups});return true}return false},cleargroups:function(){var b=this;b.groups=[];b.dataview.groups=b.groups;b.refreshgroups();this._raiseEvent(12,{type:"Clear",index:-1,groups:b.groups});return true},removegroup:function(c){if(c==null){return false}var b=this.groups.indexOf(c.toString());return this.removegroupat(b)},getrootgroupscount:function(){var b=this.dataview.loadedrootgroups.length;return b},collapsegroup:function(b){return this._setrootgroupstate(b,false)},expandgroup:function(b){return this._setrootgroupstate(b,true)},collapseallgroups:function(b){this._setbatchgroupstate(false,b)},expandallgroups:function(b){this._setbatchgroupstate(true,b)},getgroup:function(e){var j=this.dataview.loadedrootgroups[e];if(j==null){return null}var f=this.expandedgroups[j.uniqueid].expanded;var g=j.group;var b=j.level;var c=new Array();this._getsubgroups(c,j);var i=this;var d={group:g,level:b,expanded:f,subgroups:c};if(j.subItems){var h=new Array();a.each(j.subItems,function(){var k=this.boundindex;h[h.length]=i.getrowdata(k)});if(h.length>0){d.subrows=h}}return d},getrootgroups:function(){var d=this.dataview.loadedrootgroups.length;var c=new Array();for(var b=0;b<d;b++){c[b]=this.getgroup(b)}return c},_getsubgroups:function(c,j){var i=this;for(obj in j.subGroups){var g=j.subGroups[obj];var e=i.expandedgroups[g.uniqueid].expanded;var f=g.group;var b=g.level;c[c.length]={group:f,level:b,expanded:e};if(g.subItems){var h=new Array();a.each(g.subItems,function(){var k=this.boundindex;h[h.length]=i.getrowdata(k)});c[c.length-1].subrows=h}if(g.subGroups){var d=new Array();i._getsubgroups(d,g)}}return c},_setbatchgroupstate:function(b,e){var c=this;for(obj in this.dataview.loadedrootgroups){c._setrootgroupstate(obj,b,false,true)}if(e==false){c._requiresupdate=true;c._renderrows(c.virtualsizeinfo);return true}var d=this.vScrollBar[0].style.visibility;this.rendergridcontent(true,false);if(d!=this.vScrollBar[0].style.visibility||this._hiddencolumns){this._updatecolumnwidths();this._updatecellwidths();this._renderrows(this.virtualsizeinfo)}return true},_setrootgroupstate:function(d,b,f,c){if(d==undefined||d==null||d<0){return false}if(!this.groupable||this.groups.length==0){return false}var j=f!=undefined?f:true;if(d>=0&&d<this.dataview.loadedrootgroups.length){var h=this.dataview.loadedrootgroups[d];if(this.pageable){var g=new Array();for(var e=0;e<this.dataview.rows.length;e++){if(this.dataview.rows[e].group!=null&&this.dataview.rows[e].level===0){g.push(this.dataview.rows[e])}}h=g[d];if(!h){return}}return this._setgroupstate(h,b,j,c)}return false},_togglegroupstate:function(e,f){if(e==null||e==undefined){return false}var c=this.vScrollInstance.value;var d=this.expandedgroups[e.uniqueid];if(d==undefined){d=false}else{d=d.expanded}d=!d;var b=this._setgroupstate(e,d,f);if(c!==0&&this.vScrollBar.css("visibility")!=="hidden"){if(c<=this.vScrollInstance.max){this.vScrollInstance.setPosition(c)}else{this.vScrollInstance.setPosition(this.vScrollInstance.max)}}return b},_setgroupstate:function(f,b,h,c){if(f==null||f==undefined){return false}var e=false;if(this.editable&&this.editcell){this.endcelledit(this.editcell.row,this.editcell.column,false,false)}var d=this.expandedgroups[f.uniqueid];if(d==undefined){d={expanded:false};e=true}if(d.expanded!=b){e=true}if(e){this.expandedgroups[f.uniqueid]={expanded:b,group:f};this._setsubgroupsvisibility(this,f,!b,c);if(h){var g=this.vScrollBar[0].style.visibility;this.rendergridcontent(true,false);if(g!=this.vScrollBar[0].style.visibility||this._hiddencolumns){this._updatecolumnwidths();this._updatecellwidths();this._renderrows(this.virtualsizeinfo)}}if(undefined==this.suspendgroupevents||this.suspendgroupevents==false){if(b){this._raiseEvent(4,{group:f.group,parentgroup:f.parentItem?f.parentItem.group:null,level:f.level,visibleindex:f.visibleindex})}else{this._raiseEvent(5,{group:f.group,parentgroup:f.parentItem?f.parentItem.group:null,level:f.level,visibleindex:f.visibleindex})}}return true}return false},_setgroupitemsvisibility:function(c,e,d){for(var b=0;b<e.subItems.length;b++){c._setrowvisibility(e.subItems[b].visibleindex,d,false)}},_setsubgroupsvisibility:function(c,g,f,d){if(g.parentItem!=null){if(this.hiddens[g.parentItem.visibleindex]){return}}else{if(g.parentItem==null){if(this.hiddens[g.visibleindex]){return}}}for(subGroup in g.subGroups){var e=g.subGroups[subGroup];if(!f){c._setrowvisibility(e.visibleindex,f,false)}var b=!f;if(!d){if(c.expandedgroups[e.uniqueid]==undefined){b=false}else{b=c.expandedgroups[e.uniqueid].expanded}}else{this.expandedgroups[e.uniqueid]={expanded:b,group:e}}if(e.subGroups){if(e.subGroups.length>0){c._setsubgroupsvisibility(c,e,!b||f,d)}else{if(e.subItems.length>0){c._setgroupitemsvisibility(c,e,!b||f)}}}if(f){c._setrowvisibility(e.visibleindex,f,false)}}if(g.subItems&&g.subItems.length>0){c._setgroupitemsvisibility(c,g,f)}},_handlecolumnsdragdrop:function(){var d=this;var g=-1;var c=false;if(!d.groupable){return}var f="mousemove.grouping"+this.element.id;var e="mousedown.grouping"+this.element.id;var h="mouseup.grouping"+this.element.id;var b=false;if(this.isTouchDevice()&&this.touchmode!==true){b=true;f=a.jqx.mobile.getTouchEventName("touchmove")+".grouping"+this.element.id;e=a.jqx.mobile.getTouchEventName("touchstart")+".grouping"+this.element.id;h=a.jqx.mobile.getTouchEventName("touchend")+".grouping"+this.element.id}this.removeHandler(a(document),f);this.addHandler(a(document),f,function(j){if(!d.showgroupsheader){return true}if(d.dragcolumn!=null){var l=parseInt(j.pageX);var u=parseInt(j.pageY);if(b){var r=d.getTouches(j);var p=r[0];l=parseInt(p.pageX);u=parseInt(p.pageY)}var n=d.host.coord();var v=parseInt(n.left);var w=parseInt(n.top);if(d.dragmousedownoffset==undefined||d.dragmousedownoffset==null){d.dragmousedownoffset={left:0,top:0}}var t=parseInt(l)-parseInt(d.dragmousedownoffset.left);var i=parseInt(u)-parseInt(d.dragmousedownoffset.top);d.dragcolumn.css({left:t+"px",top:i+"px"});c=false;if(l>=v&&l<=v+d.host.width()){if(u>=w&&u<=w+d.host.height()){c=true}}g=-1;if(c){d.dragcolumnicon.removeClass(d.toThemeProperty("jqx-grid-dragcancel-icon"));d.dragcolumnicon.addClass(d.toThemeProperty("jqx-grid-drag-icon"));var s=d.groupsheader.coord();var m=s.top+d.groupsheader.height();var q=a.data(d.dragcolumn[0],"datarecord");if(q){var k=d.groups.indexOf(q.toString())}else{var k=-1}var o=(k==-1)||(d.groups.length>1&&k>-1);if(d.dropline!=null){if(u>=s.top&&u<=m){if(o){g=d._handlegroupdroplines(l)}}else{d.dropline.fadeOut("slow")}}}else{if(d.dropline!=null){d.dropline.fadeOut("slow")}d.dragcolumnicon.removeClass(d.toThemeProperty("jqx-grid-drag-icon"));d.dragcolumnicon.addClass(d.toThemeProperty("jqx-grid-dragcancel-icon"))}if(b){j.preventDefault();j.stopPropagation();return false}}});this.removeHandler(a(document),h);this.addHandler(a(document),h,function(i){if(!d.showgroupsheader){return true}d.__drag=false;a(document.body).removeClass("jqx-disableselect");var m=parseInt(i.pageX);var s=parseInt(i.pageY);if(b){var p=d.getTouches(i);var o=p[0];m=parseInt(o.pageX);s=parseInt(o.pageY)}var n=d.host.coord();var t=parseInt(n.left);var u=parseInt(n.top);var k=d.groupsheader.height();if(d.showtoolbar){u+=d.toolbarheight}d.dragstarted=false;d.dragmousedown=null;if(d.dragcolumn!=null){var l=a.data(d.dragcolumn[0],"datarecord");d.dragcolumn.remove();d.dragcolumn=null;if(l!=null){if(!d.getcolumn(l).groupable){if(d.dropline!=null){d.dropline.remove();d.dropline=null}return}if(c){if(g!=-1){var q=g.index;var r=d.groups[q];var j=d._getGroupIndexByDataField(l);if(j!=q){if(j!=undefined&&j>=0){d.groups.splice(j,1)}if(g.position=="before"){if(!d.rtl){d._insertbeforegroup(r,l)}else{d._insertaftergroup(r,l)}}else{if(!d.rtl){d._insertaftergroup(r,l)}else{d._insertbeforegroup(r,l)}}}}else{if(d.groups.length==0){if(s>u&&s<=u+k){d.addgroup(l)}}else{if(s>u+k){var j=d._getGroupIndexByDataField(l);d.removegroupat(j)}}}}if(d.dropline!=null){d.dropline.remove();d.dropline=null}}}})},_getGroupIndexByDataField:function(b){for(var c=0;c<this.groups.length;c++){if(this.groups[c]==b){return c}}return -1},_isColumnInGroups:function(c){for(var b=0;b<this.groups.length;b++){if(this.groups[b]==c){return true}}return false},_handlegroupdroplines:function(d){var b=this;var c=-1;a.each(b.groupheadersbounds,function(e){if(d<=this.left+this.width/2){var f=this.left-3;if(e>0){f=this.left-1-b.groupindentwidth/6}b.dropline.css("left",f);b.dropline.css("top",this.top);b.dropline.height(this.height);b.dropline.fadeIn("slow");c={index:e,position:"before"};if(b.rtl){c={index:b.groupheadersbounds.length-1-e,position:"before"}}return false}else{if(d>=this.left+this.width/2){b.dropline.css("left",1+this.left+this.width);b.dropline.css("top",this.top);b.dropline.height(this.height);b.dropline.fadeIn("slow");c={index:e,position:"after"};if(b.rtl){c={index:b.groupheadersbounds.length-1-e,position:"after"}}}}});return c},_handlegroupstocolumnsdragdrop:function(c,e){this.dragmousedown=null;this.dragmousedownoffset=null;this.dragstarted=false;this.dragcolumn=null;var f=this;var d;var h="mousedown";var g="mousemove";var b=false;if(this.isTouchDevice()&&this.touchmode!==true){b=true;h=a.jqx.mobile.getTouchEventName("touchstart");g=a.jqx.mobile.getTouchEventName("touchmove")}this.addHandler(e,"dragstart",function(i){return false});this.addHandler(e,h,function(i){if(!f.showgroupsheader){return true}var l=i.pageX;var k=i.pageY;f.__drag=true;f.dragmousedown={left:l,top:k};if(b){var j=f.getTouches(i);var n=j[0];l=n.pageX;k=n.pageY;f.dragmousedown={left:l,top:k};if(i.preventDefault){i.preventDefault()}}var m=a(i.target).coord();f.dragmousedownoffset={left:parseInt(l)-parseInt(m.left),top:parseInt(k-m.top)}});this.addHandler(e,g,function(i){if(!f.showgroupsheader){return true}if(f.dragmousedown){d={left:i.pageX,top:i.pageY};if(b){var k=f.getTouches(i);var m=k[0];d={left:m.pageX,top:m.pageY}}if(!f.dragstarted&&f.dragcolumn==null){var j=Math.abs(d.left-f.dragmousedown.left);var l=Math.abs(d.top-f.dragmousedown.top);if(j>3||l>3){f._createdragcolumn(e,d,true);a(document.body).addClass("jqx-disableselect");a.data(f.dragcolumn[0],"datarecord",c);if(i.preventDefault){i.preventDefault()}}}}})},_createdragcolumn:function(c,e,g){var h=this;var f=e;h.dragcolumn=a("<div></div>");var j=c.clone();h.dragcolumn.css("z-index",999999);j.css("border-width","1px");j.css("opacity","0.4");var i=a(j.find("."+h.toThemeProperty("jqx-grid-column-menubutton")));if(i.length>0){i.css("display","none")}var b=a(j.find(".jqx-icon-close"));if(b.length>0){b.css("display","none")}h.dragcolumnicon=a('<div style="z-index: 9999; position: absolute; left: 100%; top: 50%; margin-left: -18px; margin-top: -7px;"></div>');h.dragcolumnicon.addClass(h.toThemeProperty("jqx-grid-drag-icon"));h.dragcolumn.css("float","left");h.dragcolumn.css("position","absolute");var d=h.host.coord();j.width(c.width()+16);h.dragcolumn.append(j);h.dragcolumn.height(c.height());h.dragcolumn.width(j.width());h.dragcolumn.append(h.dragcolumnicon);a(document.body).append(h.dragcolumn);j.css("margin-left",0);j.css("left",0);j.css("top",0);h.dragcolumn.css("left",f.left+h.dragmousedown.left);h.dragcolumn.css("top",f.top+h.dragmousedown.top);if(g!=undefined&&g){h.dropline=a('<div style="display: none; position: absolute;"></div>');h.dropline.width(2);h.dropline.addClass(h.toThemeProperty("jqx-grid-group-drag-line"));a(document.body).append(h.dropline)}},iscolumngroupable:function(b){return this._getcolumnproperty(b,"groupable")},_handlecolumnstogroupsdragdrop:function(c,f){this.dragmousedown=null;this.dragmousedownoffset=null;this.dragstarted=false;this.dragcolumn=null;var g=this;var e;var b=false;if(this.isTouchDevice()&&this.touchmode!==true){b=true}var d="mousedown.drag";var e="mousemove.drag";if(b){d=a.jqx.mobile.getTouchEventName("touchstart")+".drag";e=a.jqx.mobile.getTouchEventName("touchmove")+".drag"}else{this.addHandler(f,"dragstart",function(h){return false})}this.addHandler(f,d,function(j){if(!g.showgroupsheader){return true}g.__drag=true;if(g._isColumnInGroups(c.displayfield)){if(f.css("cursor")!="col-resize"){return true}else{return true}}if(false==c.groupable){return true}var i=j.pageX;var h=j.pageY;if(b){var k=g.getTouches(j);var m=k[0];i=m.pageX;h=m.pageY}g.dragmousedown={left:i,top:h};if(b){if(j.preventDefault){j.preventDefault()}}var l=a(j.target).coord();g.dragmousedownoffset={left:parseInt(i)-parseInt(l.left),top:parseInt(h-l.top)}});this.addHandler(f,e,function(j){if(!g.showgroupsheader){return true}if(g._isColumnInGroups(c.displayfield)){if(f.css("cursor")!="col-resize"){return true}else{return true}}if(g.dragmousedown){var i=j.pageX;var h=j.pageY;if(b){var l=g.getTouches(j);var n=l[0];i=n.pageX;h=n.pageY}e={left:i,top:h};if(!g.dragstarted&&g.dragcolumn==null){var k=Math.abs(e.left-g.dragmousedown.left);var m=Math.abs(e.top-g.dragmousedown.top);if(k>3||m>3){g._createdragcolumn(f,e,true);a.data(g.dragcolumn[0],"datarecord",c.displayfield);if(j.preventDefault){j.preventDefault()}}}}})},_rendergroupcolumn:function(g,h){var e=a('<div style="float: left; position: relative;"></div>');if(this.rtl){e.css("float","right")}if(this.groupcolumnrenderer!=null){e[0].innerHTML=this.groupcolumnrenderer(g);e.addClass(this.toThemeProperty("jqx-grid-group-column"));e.addClass(this.toThemeProperty("jqx-fill-state-normal"))}if(this.closeablegroups){if(e[0].innerHTML==""){e[0].innerHTML='<a style="float: left;" href="#">'+g+"</a>"}if(this.rtl){e[0].innerHTML='<a style="float: right;" href="#">'+g+"</a>"}var d=!this.rtl?"right":"left";var c='<div style="float: '+d+'; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -18px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-icon-close")+'"></div></div>';if(a.jqx.browser.msie&&a.jqx.browser.version<8){c='<div style="float: left; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -18px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-icon-close")+'"></div></div>'}if(this.rtl){var c='<div style="float: '+d+'; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-icon-close")+'"></div></div>';if(a.jqx.browser.msie&&a.jqx.browser.version<8){c='<div style="float: left; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-icon-close")+'"></div></div>'}}e[0].innerHTML+=c}else{if(e[0].innerHTML==""){e[0].innerHTML='<a href="#">'+g+"</a>"}}if(this.sortable){var f=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -16px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortascbutton")+'"></div></div>');var b=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -16px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortdescbutton")+'"></div></div>');if(this.closeablegroups){var f=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -32px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortascbutton")+'"></div></div>');var b=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 100%; top: 50%; margin-left: -32px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortdescbutton")+'"></div></div>')}if(this.rtl){var f=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-left: 0px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortascbutton")+'"></div></div>');var b=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-left: 0px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortdescbutton")+'"></div></div>');if(this.closeablegroups){var f=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-left: 16px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortascbutton")+'"></div></div>');var b=a('<div style="float: right; min-height: 16px; min-width: 18px;"><div style="position: absolute; left: 0px; top: 50%; margin-left: 16px; margin-top: -8px; float: none; width: 16px; height: 16px;" class="'+this.toThemeProperty("jqx-grid-column-sortdescbutton")+'"></div></div>')}}f.css("display","none");b.css("display","none");if(a.jqx.browser.msie&&a.jqx.browser.version<8){f.css("float","left");b.css("float","left")}e.append(f);e.append(b);a.data(document.body,"groupsortelements"+h,{sortasc:f,sortdesc:b})}e.addClass(this.toThemeProperty("jqx-fill-state-normal"));e.addClass(this.toThemeProperty("jqx-grid-group-column"));return e},_rendergroup:function(o,w,b,u,j,A,t){var F=w;var c=w.cells[b.level];if(this.rtl){c=w.cells[w.cells.length-1-b.level]}var E=this._findgroupstate(b.uniqueid);if(b.bounddata.subGroups.length>0||b.bounddata.subItems.length>0){var i=this.rtl?"-rtl":"";var B=this.toThemeProperty("jqx-icon-arrow-right");if(i){B=this.toThemeProperty("jqx-icon-arrow-left")}if(E){c.className+=" "+this.toThemeProperty("jqx-grid-group-expand"+i)+" "+this.toThemeProperty("jqx-icon-arrow-down")}else{c.className+=" "+this.toThemeProperty("jqx-grid-group-collapse"+i)+" "+B}}var s=this._getColumnText(this.groups[b.level]).label;var d=this.groupindentwidth;var q=this.rowdetails&&this.showrowdetailscolumn?(1+o)*d:(o)*d;var x=t-q;var g=b.level+1;if(this.rtl){g=0}var l=F.cells[g];var z=2;while(l!=undefined&&l.style.display=="none"&&z<F.cells.length-1){l=F.cells[g+z-1];z++}var f=a(l);if(!l){return}l.style.width=parseInt(x)+"px";if(l.className.indexOf("jqx-grid-cell-filter")!=-1){f.removeClass(this.toThemeProperty("jqx-grid-cell-filter"))}if(l.className.indexOf("jqx-grid-cell-sort")!=-1){f.removeClass(this.toThemeProperty("jqx-grid-cell-sort"))}if(l.className.indexOf("jqx-grid-cell-pinned")!=-1){f.removeClass(this.toThemeProperty("jqx-grid-cell-pinned"))}if(this.groupsrenderer!=null){var h={group:b.group,level:b.level,parent:b.bounddata.parentItem,subGroups:b.bounddata.subGroups,subItems:b.bounddata.subItems,groupcolumn:this._getColumnText(this.groups[b.level]).column};var p=this.groupsrenderer(s+": "+b.group,b.group,E,h);if(p){l.innerHTML=p}else{var k=b.bounddata.subItems.length>0?b.bounddata.subItems.length:b.bounddata.subGroups.length;l.innerHTML='<div class="'+this.toThemeProperty("jqx-grid-groups-row")+'" style="position: absolute;"><span>'+s+': </span><span class="'+this.toThemeProperty("jqx-grid-groups-row-details")+'">'+b.group+" ("+k+")</span></div>"}}else{var e=this._getcolumnbydatafield(this.groups[b.level]);var y=b.group;if(e!=null){if(e.cellsformat){if(a.jqx.dataFormat){if(a.jqx.dataFormat.isDate(y)){y=a.jqx.dataFormat.formatdate(y,e.cellsformat,this.gridlocalization)}else{if(a.jqx.dataFormat.isNumber(y)){y=a.jqx.dataFormat.formatnumber(y,e.cellsformat,this.gridlocalization)}}}}var k=b.bounddata.subItems.length>0?b.bounddata.subItems.length:b.bounddata.subGroups.length;l.innerHTML='<div class="'+this.toThemeProperty("jqx-grid-groups-row")+'" style="position: absolute;"><span>'+s+': </span><span class="'+this.toThemeProperty("jqx-grid-groups-row-details")+'">'+y+" ("+k+")</span></div>"}else{throw new Error("jqxGrid: Unable to find '"+this.groups[b.level]+"' group in the Grid's columns collection.")}}if(this.rtl){if(!e){e=this._getcolumnbydatafield(this.groups[b.level])}var r=this.hScrollBar.css("visibility")=="hidden"?0:this.hScrollInstance.max-this.hScrollInstance.value;var C=this.vScrollBar.css("visibility")=="hidden"?0:this.scrollbarsize+6;var q=this.rowdetails&&this.showrowdetailscolumn?(2+b.level)*d:(1+b.level)*d;l.style.width=t+parseInt(r)-q-C+"px";f.addClass(this.toThemeProperty("jqx-rtl"));var v=a(w.cells[w.cells.length-1]).css("z-index");f.css("z-index",v);var D=f.find("div");var x=D.width();D.css("left","100%");var n=this.columns.records[w.cells.length-2-b.level]!=null?this.columns.records[w.cells.length-2-b.level].pinned:false;if(this.table.width()<t){t=this.table.width();if(this.vScrollBar.css("visibility")!="hidden"){t+=this.vScrollBar.outerWidth()}}if(e.pinned||n){if(this.rowdetails&&this.showrowdetailscolumn){t+=30}D.css("margin-left",-x);l.style.width=t+r-q-C+"px"}else{var r=this.hScrollBar.css("visibility")=="hidden"?0:this.hScrollInstance.max;l.style.width=t+r-q-C+"px";var x=D.width();D.css("margin-left",-x)}}}})})(jqxBaseFramework);});