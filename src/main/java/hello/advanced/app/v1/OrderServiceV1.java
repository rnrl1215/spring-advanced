package hello.advanced.app.v1;

import hello.advanced.trace.HelloTraceV1.HelloTraceV2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status =  null;
        try {
            status = trace.beginSync(traceId, "OrderServiceV1.orderItem()");
            orderRepository.save(traceId, itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            // 예외를 다시 던져 줘야 한다.
            throw e;
        }

    }

}
