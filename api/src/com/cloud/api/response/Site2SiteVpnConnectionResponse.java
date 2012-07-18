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
package com.cloud.api.response;

import java.util.Date;

import com.cloud.api.ApiConstants;
import com.cloud.utils.IdentityProxy;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Site2SiteVpnConnectionResponse extends BaseResponse {
    @SerializedName(ApiConstants.ID) @Param(description="the vpn gateway ID")
    private IdentityProxy id = new IdentityProxy("s2s_vpn_connection");

    @SerializedName(ApiConstants.S2S_VPN_GATEWAY_ID) @Param(description="the vpn gateway ID")
    private IdentityProxy vpnGatewayId= new IdentityProxy("s2s_vpn_gateway");

    @SerializedName(ApiConstants.S2S_CUSTOMER_GATEWAY_ID) @Param(description="the customer gateway ID")
    private IdentityProxy customerGatewayId = new IdentityProxy("s2s_customer_gateway");

    @SerializedName(ApiConstants.CREATED) @Param(description="the date and time the host was created")
    private Date created;

    @SerializedName(ApiConstants.REMOVED) @Param(description="the date and time the host was removed")
    private Date removed;

	public void setId(Long id) {
		this.id.setValue(id);
	}
	
    public void setVpnGatewayId(Long vpnGatewayId) {
        this.vpnGatewayId.setValue(vpnGatewayId);
    }

    public void setCustomerGatewayId(Long customerGatewayId) {
        this.customerGatewayId.setValue(customerGatewayId);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setRemoved(Date removed) {
        this.removed = removed;
    }	

}
