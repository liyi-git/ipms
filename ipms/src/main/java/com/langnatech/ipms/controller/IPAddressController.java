package com.langnatech.ipms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.service.IPAddressResService;


@Controller
@RequestMapping("/address")
public class IPAddressController
{
    @Autowired
    private IPAddressResService addressService;

    @RequestMapping("/registry")
    public String registry()
    {
        return "address/ip-registry";
    }

    @RequestMapping("{addressId}/register")
    public String registry(@PathVariable(value = "addressId") String addressId, ModelMap map)
    {
        map.put("ADDRESS", this.addressService.getByAddressId(addressId));
        return "address/ip-registry";
    }

    @RequestMapping("{addressId}/change")
    public String change(@PathVariable(value = "addressId") String addressId, ModelMap map)
    {
        map.put("ADDRESS", this.addressService.getByAddressId(addressId));
        return "address/ip-registry";
    }

    @RequestMapping("{addressId}/saveReg")
    @ResponseBody
    public String saveReg(@PathVariable(value = "addressId") String addressId,IPAddressResEntity addressEntity, ModelMap map)
    {
        int result=this.addressService.saveAddressRegistry(addressEntity);
        if(result>0){
            return "{\"success\":true}";
        }else{
            return "{\"success\":false}";
        }
    }
}
