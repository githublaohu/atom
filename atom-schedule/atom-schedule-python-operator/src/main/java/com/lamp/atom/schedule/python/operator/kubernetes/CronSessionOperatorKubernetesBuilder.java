package com.lamp.atom.schedule.python.operator.kubernetes;

import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.fabric8.kubernetes.api.model.batch.v1.CronJobBuilder;
import io.fabric8.kubernetes.api.model.batch.v1.CronJobFluent.MetadataNested;
import io.fabric8.kubernetes.api.model.batch.v1.CronJobFluent.SpecNested;

public class CronSessionOperatorKubernetesBuilder extends AbstractOperatorKubernetesBuilder{

	private CronJobBuilder cronJobBuilder = new CronJobBuilder();
	
	private void cronJob() {
		cronJobBuilder.withApiVersion("batch/v1");
		cronJobBuilder.withKind("CronJob");
	}

	private void metadata() {
		MetadataNested<CronJobBuilder> metadata = cronJobBuilder.withNewMetadata();
		metadata.withName("atom-runtime-cronsession-"+this.schedule.getNodeName()).withLabels(schedule.getLabels());
		metadata.endMetadata();
	}

	public void spec() {
		SpecNested<CronJobBuilder> spec = cronJobBuilder.withNewSpec();
		spec.withSchedule(this.schedule.getStrategy().getTiming());
		spec.withNewJobTemplate()
				.withNewSpec()
				.withNewTemplate()
				.withNewSpec()
				.withRestartPolicy("Never")
				.withHostNetwork(true)
				.addNewContainer()
				.withName(this.schedule.getNodeName())
				.withImage(this.getIamges())
				.withResources(resourceRequirements)
				.withEnv(envList)
				.endContainer()
				.endSpec()
				.endTemplate()
				.endSpec();
	}
	
	public CronJob getJob() {
		this.cronJob();
		this.metadata();
		this.spec();
		return this.cronJobBuilder.build();
	}

	@Override
	String getModel() {
		return "cronsession";
	}
}
