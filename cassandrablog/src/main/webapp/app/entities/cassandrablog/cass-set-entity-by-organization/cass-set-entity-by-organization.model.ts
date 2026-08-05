export interface ICassSetEntityByOrganization {
  organizationId: string;
  tags?: Set<string> | null;
}

export type NewCassSetEntityByOrganization = Omit<ICassSetEntityByOrganization, 'organizationId'> & { organizationId: string };
