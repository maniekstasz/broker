package soa.premisebroker.config;

import java.text.ParseException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import soa.premisebroker.quartz.job.DebtCollectorSendJob;
import soa.premisebroker.quartz.job.InvoiceSendJob;
import soa.premisebroker.quartz.job.OflineReservationSendJob;

@Configuration
public class QuartzConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Environment env;

	// @Autowired
	// private DataSource dataSource;
	//
	// @Autowired
	// private PlatformTransactionManager transactionManager;

	@Bean
	public SchedulerFactoryBean quartzScheduler() throws ParseException {
		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
		// quartzScheduler.setDataSource(dataSource);
		// quartzScheduler.setTransactionManager(transactionManager);
		quartzScheduler.setOverwriteExistingJobs(true);
		// quartzScheduler.setQuartzProperties(quartzProperties());
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		quartzScheduler.setJobFactory(jobFactory);
		quartzScheduler.setTriggers(new Trigger[] {
				reservationsEmailSendTriggerFactory().getObject(),
				invoiceSendTriggerFactory().getObject(), debtCollectionTriggerFactory().getObject() });
		return quartzScheduler;
	}

	@Bean
	public JobDetailFactoryBean reservationsEmailSendJobFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(OflineReservationSendJob.class);
		jobDetailFactory.setDurability(true);
		jobDetailFactory.setGroup("batch-quartz");
		jobDetailFactory.setName("reservations_requests_email_send");
		return jobDetailFactory;
	}

	@Bean
	public JobDetailFactoryBean invoiceSendJobFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(InvoiceSendJob.class);
		jobDetailFactory.setDurability(true);
		jobDetailFactory.setGroup("batch-quartz");
		jobDetailFactory.setName("monthly_invoice_send");
		return jobDetailFactory;
	}
	
	@Bean
	public JobDetailFactoryBean debtCollectionJobFactory() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(DebtCollectorSendJob.class);
		jobDetailFactory.setDurability(true);
		jobDetailFactory.setGroup("batch-quartz");
		jobDetailFactory.setName("debt_collection_send");
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean debtCollectionTriggerFactory() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(debtCollectionJobFactory()
				.getObject());
		cronTriggerFactoryBean.setCronExpression(env
				.getProperty("cron.expressions.debtCollection"));
		cronTriggerFactoryBean.setGroup("batch-quartz");
		return cronTriggerFactoryBean;
	}
	@Bean
	public CronTriggerFactoryBean reservationsEmailSendTriggerFactory() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();

		cronTriggerFactoryBean.setJobDetail(reservationsEmailSendJobFactory()
				.getObject());
		cronTriggerFactoryBean.setCronExpression(env
				.getProperty("cron.expressions.reservationsEmailSend"));
		cronTriggerFactoryBean.setGroup("batch-quartz");
		return cronTriggerFactoryBean;
	}

	@Bean
	public CronTriggerFactoryBean invoiceSendTriggerFactory() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean
				.setJobDetail(invoiceSendJobFactory().getObject());
		cronTriggerFactoryBean.setCronExpression(env
				.getProperty("cron.expressions.invoiceSend"));
		cronTriggerFactoryBean.setGroup("batch-quartz");
		return cronTriggerFactoryBean;
	}


}
