;
(function() {

    // 注册到window对象
    var $ip = window.$ip = function(selector) {
        return new $ip.fn.init(selector);
    };

    // 版本号
    $ip.VERSION = '1.0.0(dev)';

    // 创建fn的命名空间,该内容为框架基础功能
    $ip.fn = $ip.prototype = {

        constructor: $ip,

        // initialize [初始化]
        init: function(selector) {

            //"" ,  null ,  undefined
            if (!selector) {
                selector = document;
            }
            selector = selector || document;
            if (selector.nodeType) {
                this[0] = selector;
                return this;
            }
            if (typeof selector === "string")
                this[0] = document.getElementById(selector);
            return this;
        }
    };

    // $ip.fn缓存$ip.prototype;避免频繁的操作$ip.prototype

    //内部处理了实例创建不用new去生成实例，处理了prototype保证多实例共享方法减少资源开支
    $ip.fn.init.prototype = $ip.fn;

    // 扩展$ip.js对象。用来在fn命名空间上增加新函数
    $ip.extend = $ip.fn.extend = function(obj, property) {
        if (!property) {
            property = obj;
            obj = this;
        }
        // obj用以扩展的对象，prop为扩展的函数集,如果参数只有一个，则扩展新函数到$ip对象上
        for (var i in property) {
            obj[i] = property[i];
        }
        return obj;
    };
    // 给fn添加的功能，需要先选择节点，然后才能操作

    // 调用方式： [$ip("id").method();] 
    $ip.extend($ip.prototype, {

        /**
         * isIP 判断是否是ip地址
         *
         * @param {String} ip
         * @return {boolean}
         *
         */
        isIP: function(ip) {
            var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;

            if (reg.test(ip)) {
                return true;
            } else {
                alert("请输入正确的IP地址");
                return false;
            }
        },

        /**
         * isPrivateIp 验证ip地址是否为私有地址
         *
         * @param {String} ip
         * @return {boolean}
         *
         * A: 10.0.0.0~10.255.255.255 即10.0.0.0/8
         * B:172.16.0.0~172.31.255.255即172.16.0.0/12
         * C:192.168.0.0~192.168.255.255 即192.168.0.0/16 
         */
        isPrivateIp: function(ip) {
            var flag = false,
                ipdec = this.getIpDecimal(ip);
            if (ipdec > this.getIpDecimal("10.0.0.0") && ipdec < this.getIpDecimal("10.255.255.255")) {
                flag = true;
            }

            if (ipdec > this.getIpDecimal("172.16.0.0") && ipdec < this.getIpDecimal("172.31.255.255")) {
                flag = true;
            }

            if (ipdec > this.getIpDecimal("192.168.0.0") && ipdec < this.getIpDecimal("192.168.255.255")) {
                flag = true;
            }
            return flag;

        },

        /**
         * isMask 判断是否是子网掩码
         *
         * @param {String} ip
         * @return {boolean}
         *
         */
        isMask: function(mask) {
            var reg = /^(254|252|248|240|224|192|128|0)\.0\.0\.0|255\.(254|252|248|240|224|192|128|0)\.0\.0|255\.255\.(254|252|248|240|224|192|128|0)\.0|255\.255\.255\.(254|252|248|240|224|192|128|0)$/;

            if (reg.test(mask)) {
                return true;
            } else {
                alert("请输入正确的子网掩码");
                return false;
            }
        },

        /**
         * isSupernetted 判断是否是超网
         *
         * @param {String} ip
         * @param {String} mask
         * @return {boolean}
         *
         */
        isSupernetted: function(ip, mask) {
            var defaultMask = this.getDefaultMask(this.getIpDecimal(ip));
            var uIpNum = this.getUsableAsObj(ip, mask).uIpNum;
            var flag = false;
            if (defaultMask == "255.0.0.0") {
                if (uIpNum > 16777216) {
                    alert("IP: " + ip + " is Supernetted");
                    flag = true;
                }
            } else if (defaultMask == "255.255.0.0") {
                if (uIpNum > 65536) {
                    alert("IP: " + ip + " is Supernetted");
                    flag = true;
                }
            } else if (defaultMask == "255.255.255.0") {
                if (uIpNum > 256) {
                    alert("IP: " + ip + " is Supernetted");
                    flag = true;
                }
            }
            return flag;
        },

        /**
         * getNetDecimal 获取网络地址的十进制
         *
         * @param {String/String} ip mask
         * @return {Number}
         *
         */
        getNetDecimal: function(ip, mask) {
            if (!this.isIP(ip) || !this.isMask(mask)) {
                return true;
            }

            try {
                var ipdec = this.getIpDecimal(ip),
                    maskbits = this.getMaskBits(mask);

                return this._getNetDecimal(ipdec, this.getMaskDecimal(maskbits));
            } catch (e) {
                if (typeof e == "string") e = "getNetDecimal(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }
        },

        /**
         * _getNetDecimal (私有)获取网络地址的十进制
         *
         * @param {Number/Number} ipdec maskdec
         * @return {Number}
         
         * 192.168.100.5：：11000000.10101000.01100100.00000101
         * 255.255.255.0：：11111111.11111111.11111111.00000000
         * 与    ------------------------------------------------
         * 192.168.100.0：：11000000.11111111.11111111.00000000
         *
         */
        _getNetDecimal: function(ipdec, maskdec) {
            if (typeof ipdec != "number" || typeof maskdec != "number") {
                throw "and(" + [].slice.call(arguments).join(',') + "): arguments must be numbers";
            }

            try {
                var binaryNetIp = "",
                    binaryIp = this.decimalToBinary(ipdec),
                    binaryMask = this.decimalToBinary(maskdec),
                    mlen = binaryIp.length;

                if (binaryMask.length > mlen) {
                    mlen = binaryMask.length
                }

                while (binaryIp.length < mlen) {
                    binaryIp = "0" + binaryIp;
                }

                while (binaryMask.length < mlen) {
                    binaryMask = "0" + binaryMask;
                }

                for (var i = 0; i < mlen; i++) {
                    binaryNetIp = binaryNetIp + ((binaryIp[i] == "1" && binaryMask[i] == "1") ? "1" : "0");
                }

                return this.binaryToDecimal(binaryNetIp);
            } catch (e) {
                if (typeof e == "string") e = "_getNetDecimal(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }
        },

        /**
         * getBroadcastDec 获取广播地址的十进制
         *
         * @param {String/String} ip mask
         * @return {Number}
         *
         */
        getBroadcastDec: function(ip, mask) {
            if (!this.isIP(ip) || !this.isMask(mask)) {
                return true;
            }

            try {
                var ipdec = this.getIpDecimal(ip),
                    maskbits = this.getMaskBits(mask),
                    bcastdec = 0;
                if (maskbits < 32) {
                    var hostdec = this._getHostNum(this.getMaskDecimal(maskbits), 32);
                    bcastdec = this._getBroadcastDec(ipdec, hostdec);
                }
                return bcastdec;
            } catch (e) {
                if (typeof e == "string") e = "getBroadcastDec(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }
        },

        /**
         * _getBroadcastDec (私有)获取广播地址的十进制
         *
         * @param {Number/Number} ipdec maskdec
         * @return {Number}
         
         * 192.168.100.5：：11000000.10101000.01100100.00000101
         * 255.255.255.0：：11111111.11111111.11111111.00000000
         * 与    ------------------------------------------------
         * 网络地址 192.168.100.0：：11000000.11111111.11111111.00000000
         * 将网络地址中网络地址部分不变，主机地址变为全1，结果是广播地址:
         * 192.168.100.255：11000000.11111111.11111111.111111111
         *
         */
        _getBroadcastDec: function(ipdec, maskdec) {
            if (typeof ipdec != "number" || typeof maskdec != "number") throw "or(" + [].slice.call(arguments).join(',') + "): arguments must be numbers";

            try {

                var binaryBdcast = "",
                    binaryIp = this.decimalToBinary(ipdec),
                    binaryMask = this.decimalToBinary(maskdec),
                    mlen = binaryIp.length;

                if (binaryMask.length > mlen) {
                    mlen = binaryMask.length
                }

                while (binaryIp.length < mlen) {
                    binaryIp = "0" + binaryIp;
                }

                while (binaryMask.length < mlen) {
                    binaryMask = "0" + binaryMask;
                }

                for (var i = 0; i < mlen; i++) {
                    binaryBdcast = binaryBdcast + (((binaryIp[i] == "1") || (binaryMask[i] == "1")) ? "1" : "0");
                }

                return this.binaryToDecimal(binaryBdcast);
            } catch (e) {
                if (typeof e == "string") e = "_getBroadcastDec(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }
        },

        /**
         * getIpDecimal 获取Ip地址的十进制
         *
         * @param {String} ip
         * @return {Number}
         *
         */
        getIpDecimal: function(ip) {
            if (!this.isIP(ip)) {
                return true;
            }

            var ipdec = 0,
                ipmat = ip.match(/^[ ]*([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})[ \/]*$/);

            if (ipmat) {
                for (var i = 1; i < 5; i++) {
                    var octet = parseInt(ipmat[i]);

                    if ((octet >= 0) && (octet <= 255)) {
                        ipdec = ipdec * 256 + octet;
                    } else {
                        throw "getIpDecimal(" + [].slice.call(arguments).join(',') + "): Invalid IP " + ip;
                    }
                }

            } else {
                throw "getIpDecimal(" + [].slice.call(arguments).join(',') + "): argument must be in dotted-decimal format";
            }
            return ipdec;
        },

        /**
         * decimalToBinary 十进制转二进制
         *
         * @param {Number} dec
         * @return {String}
         *
         */
        decimalToBinary: function(dec) {
            if (typeof dec != "number") {
                throw "decimalToBinary(" + [].slice.call(arguments).join(',') + "): argument must be a number";
            }

            var binary = "",
                currdec = dec;

            while (currdec != 0) {
                binary = Math.abs(currdec % 2) + binary;

                if (currdec < 0) {
                    currdec = Math.ceil(currdec / 2);
                } else {
                    currdec = Math.floor(currdec / 2);
                }
            }

            return binary
        },

        /**
         * binaryToDecimal 二进制转十进制
         *
         * @param {String} bin
         * @return {Number}
         *
         */
        binaryToDecimal: function(bin) {

            if (typeof bin != "string") {
                throw "binaryToDecimal(" + [].slice.call(arguments).join(',') + "): argument must be a string";
            }

            // 二进制只能是“0”和“1”组成
            if (bin.match(/[^01]/)) {
                throw "binaryToDecimal(" + [].slice.call(arguments).join(',') + "): argument may only contain '0' and '1'";
            }

            var decimal = 0;

            for (var i = 0; i < bin.length; i++) {

                if (decimal != undefined) {
                    decimal = decimal * 2;
                    if (bin[i] == "1") {
                        decimal += 1;
                    } else {
                        if (bin[i] != 0) {
                            decimal = undefined;
                        }
                    }
                }
            }

            return decimal;
        },

        /**
         * decimalToAddr 十进制地址转换为普通ip地址格式
         *
         * @param {Number} dec
         * @return {String}
         *
         */
        decimalToAddr: function(dec) {

            if (typeof dec != "number") {
                dec = parseInt(dec);
            }

            if (typeof dec != "number" || dec < 0) {
                throw "decimalToAddr(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + dec;
            }

            var ip = "",
                currdec = dec;

            for (var i = 0; i < 4; i++) {

                if (ip != "") {
                    ip = "." + ip;
                }
                ip = (currdec % 256) + ip;
                currdec = Math.floor(currdec / 256);
            }

            if (currdec != 0) throw "decimalToAddr(" + [].slice.call(arguments).join(',') + "): Error converting " + dec + " to IP address";

            return ip;
        },

        /**
         * getMaskBits 获取掩码对应的网络位数
         *
         * @param {String/String} ip mask
         * @return {Number}
         *
         */
        getMaskBits: function(mask) {
            if (!this.isMask(mask)) {
                return true;
            }

            var maskbits = 0,
                maskmat = mask.match(/^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$/);
            /**
             * mask        = 11111111 11111111 11111111 00000000
             * ~(mask)     = 00000000 00000000 00000000 11111111
             * ~(mask) + 1 = 00000000 00000000 00000001 00000000 = Math.pow(2, 32 - maskbits)
             *
             * a = Math.pow(b,c)  <=>  c = Math.log(a)/Math.log(b)
             * a = Math.pow(2, 32 - maskbits)
             * b = 2
             * c = 32 - maskbits
             *
             * ~(mask) + 1 = Math.pow(2, 32 - maskbits)  <=> 32 - maskbits = Math.log(~(this.mask) + 1)/Math.log(2)
             *                                           <=> maskbits = 32 - Math.log(~(this.mask) + 1)/Math.log(2)
             */
            // TODO
            // mask=Math.round(32 - Math.log(~(mask) + 1)/Math.log(2));

            if (maskmat) {

                for (var i = 0; i < 4; i++) {
                    var octet = parseInt(maskmat[i + 1]);

                    if (i == 0 && octet < 255) {
                        throw "getMaskBits(" + [].slice.call(arguments).join(',') + "): Invalid mask " + mask;
                    }
                    maskbits += (8 - (Math.log(256 - octet) / Math.log(2)));
                }

            } else {
                throw "getMaskBits(" + [].slice.call(arguments).join(',') + "): Invalid mask " + mask;
            }
            return maskbits;
        },

        /**
         * getMaskDecimal 获取子网掩码十进制
         *
         * @param {Number} maskbits 掩码位数
         * @return {Number}
         *
         */
        getMaskDecimal: function(maskbits) {
            if (maskbits < 0 || maskbits > 32) {
                throw "getMaskDecimal(" + [].slice.call(arguments).join(',') + "): invalid argument " + maskbits;
            }

            return (Math.pow(2, maskbits) - 1) * Math.pow(2, 32 - maskbits);
        },

        /**
         *根据网段数量得到掩码位数
         */
        getMaskBitsBySubnetNum: function(subnetNum) {
            var mbits = "";
            switch (subnetNum - 2) {
                case 2:
                    mbits = 30;
                    break;
                case 6:
                    mbits = 29;
                    break;
                case 14:
                    mbits = 28;
                    break;
                case 30:
                    mbits = 27;
                    break;
                case 62:
                    mbits = 26;
                    break;
                case 126:
                    mbits = 25;
                    break;
                case 254:
                    mbits = 24;
                    break;
                case 510:
                    mbits = 23;
                    break;
                case 1022:
                    mbits = 22;
                    break;
                case 2046:
                    mbits = 21;
                    break;
                case 4094:
                    mbits = 20;
                    break;
                case 8190:
                    mbits = 19;
                    break;
                case 16382:
                    mbits = 18;
                    break;
                case 32766:
                    mbits = 17;
                    break;
                case 65534:
                    mbits = 16;
                    break;
                case 131070:
                    mbits = 15;
                    break;
                case 262142:
                    mbits = 14;
                    break;
                case 524286:
                    mbits = 13;
                    break;
                case 1048574:
                    mbits = 12;
                    break;
                case 2097150:
                    mbits = 11;
                    break;
                case 4194302:
                    mbits = 10;
                    break;
                case 8388606:
                    mbits = 9;
                    break;
                case 16777214:
                    mbits = 8;
                    break;
            }
            return mbits;
        },

        /**
         * _getHostNum 获得主机数量（含广播地址和网络地址）
         *
         * @param {Number} maskdec(子网掩码十进制)
         * @return {String}
         *
         */
        _getHostNum: function(maskdec, bitlen) {
            if (typeof maskdec != "number") throw "_getHostNum(" + [].slice.call(arguments).join(',') + "): arguments must be numbers";

            try {
                var binaryHostNum = "",
                    binaryMask = this.decimalToBinary(maskdec);
                if (bitlen) {
                    while (binaryMask.length < bitlen) {
                        binaryMask = "0" + binaryMask
                    }
                }
                // 或者是2的主机位数次幂
                for (var i = 0; i < binaryMask.length; i++) {

                    if (binaryMask[i] == 1) {
                        binaryHostNum = binaryHostNum + "0";
                    } else {
                        binaryHostNum = binaryHostNum + "1";
                    }
                }

                return this.binaryToDecimal(binaryHostNum);
            } catch (e) {
                if (typeof e == "string") e = "_getHostNum(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;

            }
        },

        /**
         * getUasbleAsObj 得到相关信息并作为对象返回
         *
         * @param {String/String} ip mask
         * @return {Object} uIpNum:可用ip数量
                            firstIp:第一个可用ip
         *                  lastIp:最后一个可用ip
         *                  hostNum：网络主机数(不包含网络地址)
         *                  mask: 子网掩码
         */
        getUsableAsObj: function(ip, mask) {
            if (!this.isIP(ip) || !this.isMask(mask)) {
                return true;
            }

            try {
                var uIpNum = 0,
                    firstIp = "",
                    lastIp = "",
                    netIpDec = this.getNetDecimal(ip, mask),
                    maskBitsDec = this.getMaskBits(mask),
                    hostNum = this._getHostNum(this.getMaskDecimal(maskBitsDec)) - 1;
                uIpNum = hostNum;

                firstIp = this.decimalToAddr(netIpDec + 1);
                lastIp = this.decimalToAddr(this.getBroadcastDec(ip, mask) - 1);

                return {
                    uIpNum: uIpNum,
                    firstIp: firstIp,
                    lastIp: lastIp,
                    mask: mask,
                    hostNum: hostNum
                };

            } catch (e) {
                if (typeof e == "string") e = "getUsableAsObj(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }
        },

        /**
         * getObjBymBits 根据网络位数得到相关信息并作为对象返回
         *
         * @param {String/Number} ip maskbits
         * @return {Object} uIpNum:可用ip数量
                            firstIp:第一个可用ip
         *                  lastIp:最后一个可用ip
         *                  hostNum：网络主机数
         *                  mask: 子网掩码
         */
        getObjBymBits: function(ip, maskbits) {
            if (!this.isIP(ip)) {
                return true;
            }
            if (typeof maskbits != "number") {
                maskbits = parseInt(maskbits);
            }
            if (typeof maskbits != "number" || maskbits < 0) {
                throw "getObjBymBits(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + maskbits;
            }
            var mask = this.decimalToAddr(this.getMaskDecimal(maskbits));

            return this.getUsableAsObj(ip, mask);

        },

        /**
         * getObjBySubnetNum 通过ip地址和子网数量来拆分IP
         *
         * @param {String/String} ip mask
         * @return {Object} uIpNum:可用ip数量
                            firstIp:第一个可用ip
         *                  lastIp:最后一个可用ip
         *                  hostNum：网络主机数(不包含网络地址)
         *                  mask: 子网掩码
         *
         */
        getObjBySubnetNum: function(ip, subnetnum) {
            if (!this.isIP(ip)) {
                return true;
            }

            if (typeof subnetnum != "number") {
                subnetnum = parseInt(subnetnum);
            }

            if (typeof subnetnum != "number" || subnetnum < 0) {
                throw "getObjBySubnetNum(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + subnetnum;
            }
            try {
                var binarySnum = this.decimalToBinary(subnetnum),
                    binaryIp = this.decimalToBinary(this.getIpDecimal(ip)),
                    slen = binarySnum.length,
                    defaultMask = this.getDefaultMask(this.getIpDecimal(ip)),
                    binDefaultmask = this.decimalToBinary(this.getIpDecimal(defaultMask)),
                    binaryMask = binDefaultmask;

                if (defaultMask == "255.0.0.0") {
                    for (var i = 0; i < slen; i++) {
                        binaryMask = this.replaceChar(binaryMask, 9 + i, 1);
                    }
                }

                if (defaultMask == "255.255.0.0") {
                    for (var i = 0; i < slen; i++) {
                        binaryMask = this.replaceChar(binaryMask, 17 + i, 1);

                    }
                }

                if (defaultMask == "255.255.255.0") {
                    for (var i = 0; i < slen; i++) {
                        binaryMask = this.replaceChar(binaryMask, 25 + i, 1);
                    }

                }
                return this.getUsableAsObj(ip, this.decimalToAddr(this.binaryToDecimal(binaryMask)));
            } catch (e) {
                if (typeof e == "string") e = "getObjBySubnetNum(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;

            }

        },

        /**
         * 根据要拆分子网数量得到拆分掩码位数
         */
        getSplitMbitsBySubNum: function(subnetIpCount, subnetNum) {
            var i = 0;
            var splitSubnetNum = 1;
            subnetIpCount=parseInt(subnetIpCount);
            subnetNum=parseInt(subnetNum);
            if (subnetNum > 0 & (subnetNum & (subnetNum - 1)) == 0) {
                splitSubnetNum = subnetNum;
            } else {
                if (subnetNum <= 0) {
                    splitSubnetNum = 2;
                } else {
                    while ((splitSubnetNum = Math.pow(2, i)) <= subnetNum) {
                        i++;
                    }
                }
            }
            splitSubnetNum = (subnetIpCount + 2) / splitSubnetNum;
            if (splitSubnetNum < 1) {
                alert("请输入能除尽 " + (subnetIpCount + 2) + "的正整数");
                return false;
            }
            return this.getMaskBitsBySubnetNum(splitSubnetNum);
        },

        /**
         * 根据需要的子网内IP数量（ipCount）得到拆分掩码位数
         */
        getSplitMbitsByIpNum: function(subnetIpCount, ipCount) {
            var i = 0;
            var splitSubnetNum = 1;
            subnetIpCount=parseInt(subnetIpCount);
            subnetNum=parseInt(ipCount);
            
            if (ipCount > 0 & (ipCount & (ipCount - 1)) == 0) {
            	if(ipCount=2){
            		ipCount=ipCount*2;
            	}
                splitSubnetNum = ipCount;
            } else {
                if (ipCount <= 0) {
                    splitSubnetNum = 4;
                } else {
                    while ((splitSubnetNum = Math.pow(2, i)) <= ipCount) {
                        i++;
                    }
                }
            }
            splitSubnetNum = (subnetIpCount + 2) / splitSubnetNum;
            if (splitSubnetNum < 1) {
                alert("请输入能除尽 " + (subnetIpCount + 2) + "的正整数");
                return false;
            }
            
            return this.getSplitMbitsBySubNum(subnetIpCount,splitSubnetNum);
        },
        
        /**
         * getObjByHostNum 通过ip地址和每个网络的主机数来拆分IP
         *
         * @param {String/Number} ip hostnum
         * @return {Object} uIpNum:可用ip数量
                            firstIp:第一个可用ip
         *                  lastIp:最后一个可用ip
         *                  hostNum：网络主机数(不包含网络地址)
         *                  mask: 子网掩码
         *
         */
        getObjByHostNum: function(ip, hostnum) {
            if (!this.isIP(ip)) {
                return true;
            }

            if (typeof hostnum != "number") {
                hostnum = parseInt(hostnum);
            }

            if (typeof hostnum != "number" || hostnum < 0) {
                throw "getObjBySubnetNum(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + hostnum;
            }

            try {
                var binHostNum = this.decimalToBinary(hostnum),
                    hlen = binHostNum.length,
                    specialMask = this.decimalToBinary(this.getIpDecimal("255.255.255.255")),
                    binaryMask = specialMask;

                for (var i = 0; i < hlen; i++) {

                    binaryMask = this.replaceChar(binaryMask, specialMask.length - i, 0);
                }

                return this.getUsableAsObj(ip, this.decimalToAddr(this.binaryToDecimal(binaryMask)));
            } catch (e) {
                if (typeof e == "string") e = "getObjBySubnetNum(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;

            }
        },

        /**
         * getObjByIpNum 通过ip地址和所需ip数量拆分子网
         *
         * @param {String/Number} ip hostnum
         * @return {Object} uIpNum:可用ip数量
                            firstIp:第一个可用ip
         *                  lastIp:最后一个可用ip
         *                  hostNum：网络主机数(不包含网络地址)
         *                  mask: 子网掩码
         *
         */
        getObjByIpNum: function(ip, ipnum) {
            if (!this.isIP(ip)) {
                return true;
            }

            if (typeof ipnum != "number") {
                ipnum = parseInt(ipnum);
            }

            if (typeof ipnum != "number" || ipnum < 0) {
                throw "getObjByIpNum(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + ipnum;
            }

            try {

                //TODO 对数函数计算掩码位数
                ipnum = parseInt(ipnum);
                var expval = parseInt(Math.log(ipnum) / Math.log(2)) + 1,
                    maxaddrval = Math.pow(2, expval);

                if (maxaddrval - ipnum < 2) {
                    expval += 1;
                }

                var maskbits = (32 - expval),
                    mask = this.decimalToAddr(this.getMaskDecimal(maskbits));

                return this.getUsableAsObj(ip, mask);
            } catch (e) {
                if (typeof e == "string") e = "getObjBySubnetNum(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;

            }

        },

        /**
         * getDefaultMask 得到默认网络掩码
         *
         * @param {Number} ipdec
         * @return {String}
         * A类地址：0.0.0.0~126.255.255.255，默认网络掩码为：255.0.0.0
         * B类地址：128.0.0.0~191.255.255.255，默认网络掩码为：255.255.0.0
         * C类地址：192.0.0.0~223.255.255.255，默认网络掩码为：255.255.255.0
         */
        getDefaultMask: function(ipdec) {
            if (typeof ipdec != "number") {
                ipdec = parseInt(ipdec);
            }

            if (typeof ipdec != "number") {
                throw "getDefaultMask(" + [].slice.call(arguments).join(',') + "): Invalid argument: " + ipdec;
            }

            var mask = 0;

            if (ipdec > this.getIpDecimal("0.0.0.0") && ipdec < this.getIpDecimal("126.255.255.255")) {
                mask = "255.0.0.0";
            }

            if (ipdec > this.getIpDecimal("128.0.0.0") && ipdec < this.getIpDecimal("191.255.255.255")) {
                mask = "255.255.0.0";
            }

            if (ipdec > this.getIpDecimal("192.0.0.0") && ipdec < this.getIpDecimal("223.255.255.255")) {
                mask = "255.255.255.0";
            }

            return mask;
        },
        /**
         * getSubnetJson 得到子网数量 
         *
         * @param {String/String} ip mask
         * @return {json Object}
         * 子网数=2*N（N代表网络位借用主机的位数）
         * 
         */
        getSubnetJson: function(ip, maskbits) {
            var mask = this.decimalToAddr(this.getMaskDecimal(parseInt(maskbits)));
            if (!this.isIP(ip) || !this.isMask(mask)) {
                return true;
            }
            try {
                var defaultMask = this.getDefaultMask(this.getIpDecimal(ip)),
                    binaryMask = this.decimalToBinary(this.getIpDecimal(mask)),
                    subnetNum = 0, // 可划分子网数量
                    subnetList = [],
                    blockSize = 0;
                var addrAry = this.getAddrAry(ip);
                var nbits = 0;
                if (this.isSupernetted(ip, mask)) {
                    var netIp = this.decimalToAddr(this.getNetDecimal(ip, mask));
                    var firstIp = this.decimalToAddr(this.getNetDecimal(ip, mask) + 1);
                    var lastIp = this.decimalToAddr(this.getBroadcastDec(ip, mask) - 1);
                    var brandIp = this.decimalToAddr(this.getBroadcastDec(ip, mask));

                    subnetList.push({
                        "netIp": netIp,
                        "firstIp": firstIp,
                        "lastIp": lastIp,
                        "brandIp": brandIp,
                        "netMask": mask
                    });
                } else {
                    if (defaultMask == "255.0.0.0") {
                        for (var i = 8; i < 32; i++) {
                            if (binaryMask[i] == 1) {
                                nbits = nbits + 1;
                            }
                        }
                        var subnetNum = Math.pow(2, nbits),
                            count = 0,
                            i4 = 0,
                            i3 = 0,
                            i2 = 0,
                            j = 0,
                            topi4 = 0,
                            topi3 = 0,
                            topi2 = 0,
                            topj = 0;
                        blockSize = ((16777216 / subnetNum));
                        for (var i = 0; i < 16777216; i = i + blockSize) {
                            var jsonObj = [];
                            count = count + 1;
                            i4 = i & 255;
                            i3 = (i / 256) & 255;
                            i2 = (i / 65536) & 255;
                            j = i4 + 1;
                            topi4 = ((i + blockSize) - 1) & 255;
                            topi3 = (((i + blockSize) - 1) / 256) & 255;
                            topi2 = (((i + blockSize) - 1) / 65536) & 255;
                            topj = topi4 - 1;
                            if (subnetNum == 8388608) {
                                j = i4;
                                topi4 = (i + blockSize - 1) & 255;
                                topj = topi4;
                            }

                            var netIp = addrAry[0] + "." + i2 + "." + i3 + "." + i4;
                            var firstIp = addrAry[0] + "." + i2 + "." + i3 + "." + j;
                            var lastIp = addrAry[0] + "." + topi2 + "." + topi3 + "." + topj;
                            var brandIp = addrAry[0] + "." + topi2 + "." + topi3 + "." + topi4;

                            subnetList.push({
                                "netIp": netIp,
                                "firstIp": firstIp,
                                "lastIp": lastIp,
                                "brandIp": brandIp,
                                "netMask": mask
                            });
                            if ((count == 256) && (subnetNum > 512)) {
                                i = 16777216 - (count * blockSize);
                            }
                        }
                    }
                    // B类

                    if (defaultMask == "255.255.0.0") {
                        for (var i = 16; i < 32; i++) {
                            if (binaryMask[i] == 1) {
                                nbits = nbits + 1;
                            }
                        }
                        // 可划分子网的数量
                        var subnetNum = Math.pow(2, nbits),
                            count = 0,
                            i4 = 0,
                            i3 = 0,
                            j = 0,
                            topi4 = 0,
                            topi3 = 0,
                            topj = 0;
                        blockSize = ((65536 / subnetNum));
                        for (var i = 0; i < 65536; i = i + blockSize) {
                            var jsonObj = [];
                            count = count + 1;
                            i4 = i & 255;
                            i3 = (i / 256) & 255;
                            j = i4 + 1;
                            topi4 = ((i + blockSize) - 1) & 255;
                            topi3 = (((i + blockSize) - 1) / 256) & 255;
                            topj = topi4 - 1;
                            if (subnetNum == 32768) {
                                j = i4;
                                topi4 = (i + blockSize - 1) & 255;
                                topj = topi4;
                            }
                            var netIp = addrAry[0] + "." + addrAry[1] + "." + i3 + "." + i4;
                            var firstIp = addrAry[0] + "." + addrAry[1] + "." + i3 + "." + j;
                            var lastIp = addrAry[0] + "." + addrAry[1] + "." + topi3 + "." + topj;
                            var brandIp = addrAry[0] + "." + addrAry[1] + "." + topi3 + "." + topi4;

                            subnetList.push({
                                "netIp": netIp,
                                "firstIp": firstIp,
                                "lastIp": lastIp,
                                "brandIp": brandIp,
                                "netMask": mask
                            });
                            if ((count == 256) && (subnetNum > 512)) {
                                i = 65536 - (count * blockSize);
                            }
                        }
                    }
                    // C类
                    if (defaultMask == "255.255.255.0") {
                        for (var i = 24; i < 32; i++) {
                            if (binaryMask[i] == 1) {
                                nbits = nbits + 1;
                            }
                        }
                        // 可划分子网的数量
                        var subnetNum = Math.pow(2, nbits),
                            brand = "", //topi
                            last = "", //topj
                            j = 0;
                        blockSize = ((256 / subnetNum));
                        for (var i = 0; i < 256; i = i + blockSize) {
                            var jsonObj = [];
                            j = i + 1;
                            brand = (i + blockSize - 1) & 255;
                            last = brand - 1;
                            if (subnetNum == 128) {
                                j = i;
                                brand = (i + blockSize - 1) & 255;
                                last = brand;
                            }
                            var netIp = addrAry[0] + "." + addrAry[1] + "." + addrAry[2] + "." + i;
                            var firstIp = addrAry[0] + "." + addrAry[1] + "." + addrAry[2] + "." + j;
                            var lastIp = addrAry[0] + "." + addrAry[1] + "." + addrAry[2] + "." + last;
                            var brandIp = addrAry[0] + "." + addrAry[1] + "." + addrAry[2] + "." + brand;

                            subnetList.push({
                                "netIp": netIp,
                                "firstIp": firstIp,
                                "lastIp": lastIp,
                                "brandIp": brandIp,
                                "netMask": mask
                            });
                        }
                    }
                }
                return subnetList;
            } catch (e) {
                if (typeof e == "string") e = "getSubnetJson(" + [].slice.call(arguments).join(',') + "): " + e;
                throw e;
            }


        },
        getSplitSubnet: function(subnetBeginIp, subnetIpCount, splitMaskBit) {
            subnetIpCount = parseInt(subnetIpCount);
            splitmaskBits = parseInt(splitMaskBit);
            if (splitMaskBit < 8 || splitMaskBit > 31) {
                throw new Error("要拆分的子网掩码位数参数不正确！");
            }
            var subnetBeginIpDec = this.getIpDecimal(subnetBeginIp);
            var usableInfo = this.getObjBymBits(subnetBeginIp, splitMaskBit);
            var subnetHostNum = usableInfo.hostNum + 2;


            if (subnetHostNum > (subnetIpCount + 2)) {
                throw new Error("将要拆分的子网主机数量不能大于当前子网的主机数量！");
            }
            var splitSubnetNum = (subnetIpCount + 2) / subnetHostNum;
            var resultList = [];
            for (var i = 0; i < splitSubnetNum; i++) {
                var ipobj = {};
                ipobj.netIp = this.decimalToAddr(subnetBeginIpDec);
                ipobj.beginIp = this.decimalToAddr(subnetBeginIpDec + 1);
                subnetBeginIpDec += subnetHostNum - 1;
                ipobj.maskIp = this.decimalToAddr(this.getMaskDecimal(splitMaskBit));
                ipobj.endIp = this.decimalToAddr(subnetBeginIpDec - 1);
                subnetBeginIpDec++;
                resultList.push(ipobj);
            }
            return resultList;
        },

        getAddrAry: function(ip) {
            var ary = [],
                pos1 = ip.indexOf("."),
                pos2 = ip.indexOf(".", pos1 + 1),
                pos3 = ip.indexOf(".", pos2 + 1);

            ary[0] = ip.substring(0, pos1);
            ary[1] = ip.substring(pos1 + 1, pos2);
            ary[2] = ip.substring(pos2 + 1, pos3);
            ary[3] = ip.substring(pos3 + 1);
            return ary;
        },

        /**
         * replaceChar 替换指定位置的字符
         *
         * @param {String,Number,String} str pos text
         * @return {String}
         * 
         */
        replaceChar: function(str, pos, text) {
            return str.substr(0, pos - 1) + text + str.substring(pos, str.length);
        }

    });

})();