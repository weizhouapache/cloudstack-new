// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.api.commands;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseAsyncCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.Site2SiteVpnGatewayResponse;
import com.cloud.event.EventTypes;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.network.IpAddress;
import com.cloud.network.Site2SiteVpnGateway;

@Implementation(description="Creates site to site vpn local gateway", responseObject=Site2SiteVpnGatewayResponse.class)
public class CreateVpnGatewayCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(CreateVpnGatewayCmd.class.getName());

    private static final String s_name = "createvpngatewayresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @IdentityMapper(entityTableName="user_ip_address")
    @Parameter(name=ApiConstants.PUBLIC_IP_ID, type=CommandType.LONG, required=true, description="public ip address id of the vpn gateway")
    private Long publicIpId;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getEntityTable() {
    	return "user_ip_address";
    }
    
    public Long getPublicIpId() {
        return publicIpId;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////


    @Override
    public String getCommandName() {
        return s_name;
    }

	@Override
	public long getEntityOwnerId() {
	    IpAddress ip = _networkService.getIp(publicIpId);
	    
	    if (ip == null) {
	        throw new InvalidParameterValueException("Unable to find ip address by id=" + publicIpId);
	    }
	    
	    return ip.getAccountId();
    }

	@Override
	public String getEventDescription() {
		return "Create site-to-site VPN gateway for account " + getEntityOwnerId() + " using public ip id=" + publicIpId;
	}

	@Override
	public String getEventType() {
		return EventTypes.EVENT_S2S_VPN_GATEWAY_CREATE;
	}
	
    @Override
    public void execute(){
        Site2SiteVpnGateway result = _s2sVpnService.createVpnGateway(this);
        if (result != null) {
            Site2SiteVpnGatewayResponse response = _responseGenerator.createSite2SiteVpnGatewayResponse(result);
            response.setResponseName(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to create VPN gateway");
        }
    }
    
    @Override
    public String getSyncObjType() {
        return BaseAsyncCmd.vpcSyncObject;
    }

    @Override
    public Long getSyncObjId() {
        return getIp().getVpcId();
    }

    private IpAddress getIp() {
        IpAddress ip = _networkService.getIp(publicIpId);
        if (ip == null) {
            throw new InvalidParameterValueException("Unable to find ip address by id " + publicIpId);
        }
        return ip;
    }
}
