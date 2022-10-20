package controllers;

import java.util.concurrent.*;
import net.spy.memcached.MemcachedClient;
import play.mvc.Controller;
import java.net.InetSocketAddress;

public class Scheduler extends Controller
{

    public static void main() throws InterruptedException, ExecutionException
    {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        ScheduledFuture scheduledFuture = service.schedule(new Callable()
        {
            @Override
            public Object call() throws Exception
            {

                MemcachedClient mc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
                return "Connection successful!!"+ "\n" +
                        "set status:"+mc.set("Neel",5,"hehe").isDone() + "\n" +
                        "Get cache:"+mc.get("Neel");

            }
        },10, TimeUnit.SECONDS);
        service.shutdown();
        renderText("result= "+ scheduledFuture.get());
    }
}