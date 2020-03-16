package com.thoughtmechanix.organization.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.organization.exception.ResourceNotFoundException;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository orgRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

	public Organization getOrg(String organizationId) throws ResourceNotFoundException {
		Organization org = orgRepository.findById(organizationId);
		if (org != null) {
			return org;
		}

		throw new ResourceNotFoundException("No organization found with id="+organizationId);
	}

	public Organization saveOrg(Organization org) {
		org.setId( UUID.randomUUID().toString());
		return orgRepository.save(org);
	}

	public Organization updateOrg(String organizationId, Organization orgRequest) throws ResourceNotFoundException {
		Organization org = orgRepository.findById(organizationId);
		if (org != null) {
			logger.info("Found organization with id: {}", organizationId);

			org.setName(orgRequest.getName());
			org.setContactName(orgRequest.getContactName());
			org.setContactEmail(orgRequest.getContactEmail());
			org.setContactPhone(orgRequest.getContactPhone());

			return orgRepository.save(org);
		}

		throw new ResourceNotFoundException("No organization found with id="+organizationId);
	}

	public void deleteOrg(String organizationId) throws ResourceNotFoundException {
		Organization org = orgRepository.findById(organizationId);
		if (org != null) {
			logger.info("Found organization with id: {}", organizationId);
			orgRepository.delete(org.getId());
		} else {
			throw new ResourceNotFoundException("No organization found with id="+organizationId);
		}
	}

}
