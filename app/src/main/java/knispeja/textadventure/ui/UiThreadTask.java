package knispeja.textadventure.ui;

import android.app.Activity;

public abstract class UiThreadTask implements Runnable
{
	protected abstract void doWork();

	@Override
	public final synchronized void run()
	{
		this.doWork();
		this.notify();
	}

	public synchronized void runOnUiThread(Activity activity)
	{
		activity.runOnUiThread(this);
		try
		{
			this.wait();
		}
		catch (InterruptedException e)
		{
			return;
		}
	}
}
