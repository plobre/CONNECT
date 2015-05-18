/**
 * Copyright (c) 2009-2014, United States Government, as represented by the Secretary of Health and Human Services. All
 * rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. Neither the name of the United States Government nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
 */
package gov.hhs.fha.nhinc.admingui.model.direct;

/**
 *
 * @author jasonasmith
 */
public class DirectAgent {
    
    private int position;
    private String agentName;
    private String agentValue;
    
    /**
     *
     */
    public DirectAgent(){
        
    }

    /**
     *
     * @param position
     * @param agentName
     * @param agentValue
     */
    public DirectAgent(int position, String agentName, String agentValue) {
        this.position = position;
        this.agentName = agentName;
        this.agentValue = agentValue;
    }

    /**
     *
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     *
     * @return
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     *
     * @param agentName
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     *
     * @return
     */
    public String getAgentValue() {
        return agentValue;
    }

    /**
     *
     * @param agentValue
     */
    public void setAgentValue(String agentValue) {
        this.agentValue = agentValue;
    }
    
    
}