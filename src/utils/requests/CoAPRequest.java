package utils.requests;

import utils.CoAPMessage;

/**
 * Represents a CoAP request.
 */
public abstract class CoAPRequest extends CoAPMessage
{
     /**
      * Instantiates a new Co ap request.
      */
     public CoAPRequest()
     {
          super();
          codeClass = 0;
     }
}
