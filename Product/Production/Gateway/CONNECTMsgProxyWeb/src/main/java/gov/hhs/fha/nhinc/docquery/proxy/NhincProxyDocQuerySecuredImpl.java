package gov.hhs.fha.nhinc.docquery.proxy;

import gov.hhs.fha.nhinc.common.auditlog.AdhocQueryResponseMessageType;
import gov.hhs.fha.nhinc.common.nhinccommon.AcknowledgementType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.docquery.DocQueryAuditLog;
import gov.hhs.fha.nhinc.docquery.nhin.proxy.NhinDocQueryProxy;
import gov.hhs.fha.nhinc.docquery.nhin.proxy.NhinDocQueryProxyObjectFactory;
import javax.xml.ws.WebServiceContext;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import gov.hhs.fha.nhinc.common.nhinccommonproxy.RespondingGatewayCrossGatewayQueryRequestType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.service.WebServiceHelper;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;

/**
 *
 *
 * @author Neil Webb
 */
public class NhincProxyDocQuerySecuredImpl
{

    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(NhincProxyDocQuerySecuredImpl.class);

    public AdhocQueryResponse respondingGatewayCrossGatewayQuery(gov.hhs.fha.nhinc.common.nhinccommonproxy.RespondingGatewayCrossGatewayQuerySecuredRequestType body, WebServiceContext context)
    {
        WebServiceHelper oHelper = new WebServiceHelper();
        AdhocQueryResponse response = new AdhocQueryResponse();
        try
        {
            if (body != null)
            {
                response = (AdhocQueryResponse) oHelper.invokeSecureWebService(this, this.getClass(), "respondingGatewayCrossGatewayQueryOrch", body, context);
            } else
            {
                log.error("Failed to call the web orchestration (" + this.getClass() + ".respondingGatewayCrossGatewayQueryOrch).  The input parameter is null.");
            }
        } catch (Exception e)
        {
            log.error("Failed to call the web orchestration (" + this.getClass() + ".respondingGatewayCrossGatewayQueryOrch).  An unexpected exception occurred.  " +
                    "Exception: " + e.getMessage(), e);
        }

        return response;
    }

    public AdhocQueryResponse respondingGatewayCrossGatewayQueryOrch(gov.hhs.fha.nhinc.common.nhinccommonproxy.RespondingGatewayCrossGatewayQuerySecuredRequestType body, AssertionType assertion)
    {
        log.debug("Entering NhincProxyDocQuerySecuredImpl.respondingGatewayCrossGatewayQuery...");
        AdhocQueryResponse response = null;

        // Audit the Document Query Request Message sent on the Nhin Interface
        DocQueryAuditLog auditLog = new DocQueryAuditLog();
        AcknowledgementType ack = auditLog.auditDQRequest(body.getAdhocQueryRequest(), assertion, NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION, NhincConstants.AUDIT_LOG_NHIN_INTERFACE);

        try
        {
            log.debug("Creating NhinDocQueryProxy");
            NhinDocQueryProxyObjectFactory docQueryFactory = new NhinDocQueryProxyObjectFactory();
            NhinDocQueryProxy proxy = docQueryFactory.getNhinDocQueryProxy();

            RespondingGatewayCrossGatewayQueryRequestType request = new RespondingGatewayCrossGatewayQueryRequestType();

            request.setAdhocQueryRequest(body.getAdhocQueryRequest());
            request.setAssertion(assertion);
            request.setNhinTargetSystem(body.getNhinTargetSystem());

            log.debug("Calling NhinDocQueryProxy.respondingGatewayCrossGatewayQuery(request)");
            response = proxy.respondingGatewayCrossGatewayQuery(request.getAdhocQueryRequest(), request.getAssertion(), request.getNhinTargetSystem());
        } catch (Throwable t)
        {
            log.error("Error sending NHIN Proxy message: " + t.getMessage(), t);
            response = new AdhocQueryResponse();
            response.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");

            RegistryError registryError = new RegistryError();
            registryError.setCodeContext("Processing NHIN Proxy document retrieve");
            registryError.setErrorCode("XDSRepositoryError");
            registryError.setSeverity("Error");
            response.getRegistryErrorList().getRegistryError().add(registryError);
        }

        // Audit the Document Query Response Message received on the Nhin Interface
        AdhocQueryResponseMessageType auditMsg = new AdhocQueryResponseMessageType();
        auditMsg.setAdhocQueryResponse(response);
        auditMsg.setAssertion(assertion);
        ack = auditLog.auditDQResponse(response, assertion, NhincConstants.AUDIT_LOG_INBOUND_DIRECTION, NhincConstants.AUDIT_LOG_NHIN_INTERFACE);

        log.debug("Leaving NhincProxyDocQuerySecuredImpl.respondingGatewayCrossGatewayQuery...");
        return response;
    }
}
