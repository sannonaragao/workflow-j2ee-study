package br.com.flow.web.mb;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.flow.model.dto.UserGroupDTO;
import br.com.flow.model.dto.WorkTemplateDTO;
import br.com.flow.model.repository.UserGroupRepo;
import br.com.flow.model.repository.WorkTemplateRepo;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;
import br.com.flow.util.FacesUtil;

@Named("wktemplateMB")
@ViewScoped
public class WktemplateMB extends AncestorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8006200800499918836L;
	@Inject
	private WorkTemplateRepo wkRepo;

	@Inject
	private UserGroupRepo ugRepo;
	
	private WorkTemplateDTO workTemplateDTO;
	private Collection<WorkTemplateDTO> listTemplates;
	private TreeNode root;
	private TreeNode singleSelectedTreeNode;
	private TreeNode priorSingleSelectedTreeNode;
	private TreeNode nextSingleSelectedTreeNode;
	private Long lastNodeClick = (long) 0;
	private boolean doubleClicked=false; 
	private boolean addingNode=false;
	private List<UserGroupDTO> userGroups;
	
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			userGroups = ugRepo.listAll();
			
		}
	}

	
	public Collection<WorkTemplateDTO> getListTemplates() {
		if (this.listTemplates == null) {
			listTemplates = wkRepo.list();
		}
		return listTemplates;
	}

	public void setListTemplates(Collection<WorkTemplateDTO> listTemplates) {
		this.listTemplates = listTemplates;
	}

	public boolean isDeletedisabled() {
		if (this.workTemplateDTO == null || this.workTemplateDTO.getId() == null)
			return true;
		return false;
	}

	public WorkTemplateRepo getWkRepo() {
		return wkRepo;
	}

	public void setWkRepo(WorkTemplateRepo wkRepo) {
		this.wkRepo = wkRepo;
	}

	public WorkTemplateDTO getWorkTemplateDTO() {
		return workTemplateDTO;
	}

	public void setWorkTemplateDTO(WorkTemplateDTO workTemplateDTO) {
		this.setReadonly(true);
		this.workTemplateDTO = workTemplateDTO;

		try {
			this.refreshRootTree(workTemplateDTO);
			this.root.setSelected(true);
			this.singleSelectedTreeNode = this.root;
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CustomException) {
				addErrorMsg(((CustomException) e).getErros());
			} else {
				e.printStackTrace();
				StackTraceElement[] trace = e.getStackTrace();
				addErrorMsg("Erro interno: " + trace[0].toString(), FacesMessage.SEVERITY_ERROR);
			}
			
		}
	}

	public void insert(){
		this.setWorkTemplateDTO(new WorkTemplateDTO());
		this.setReadonly(false);
	}
	
	private void buildTreeRecursive(Set<WorkTemplateDTO> listTemplates, TreeNode parent) {

		for (WorkTemplateDTO var : listTemplates) {
			TreeNode node = new DefaultTreeNode(var, parent);
			this.buildTreeRecursive(var.getWorkTemplates(), node);
		}

	}

	public void setNodeTemplate(WorkTemplateDTO wtp){
		this.setReadonly(true);
		this.workTemplateDTO = wtp;
		
	}

	public void refreshRootTree(WorkTemplateDTO workdto) throws Exception {

		if (workdto == null) return;

		if ( workdto.getId() != null ){
			workdto = wkRepo.load(workdto.getId());
		}
		
		root = new DefaultTreeNode(workdto, null);
		root.setExpanded(true);
		root.setSelectable(true);
		this.buildTreeRecursive(workdto.getWorkTemplates(), root);
//		root.setSelected(true);
//		this.singleSelectedTreeNode = root;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSingleSelectedTreeNode() {
		return singleSelectedTreeNode;
	}

	public void setSingleSelectedTreeNode(TreeNode nodeToSet) {
//		this.singleSelectedTreeNode = singleSelectedTreeNode;
		this.applyNodeChange(nodeToSet);
		if ( nodeToSet != null && ((WorkTemplateDTO)nodeToSet.getData()).getWorkTemplateSuper() == null ){
			nodeToSet.setSelected(true);
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {
		boolean  changed = getChanges() ;
		
		this.nextSingleSelectedTreeNode	=	event.getTreeNode();
		Long currentClick = System.currentTimeMillis();
		
		if ( lastNodeClick > 0 && ( currentClick - lastNodeClick ) < 500 && !changed) doubleClicked = true;
		lastNodeClick	=	currentClick;
		
		if ( !changed ){
			applyNodeChange(nextSingleSelectedTreeNode);
		}else{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('saveDialog').show();");
		}
		
	}
	
	public void discardUpdate()  {
		
		try {
			/*
			 * Descarta as atualizações buscando os dados do banco e jogando sobre os dados alterados
			 */
//			WorkTemplateDTO reloadData = this.wkRepo.load( ((WorkTemplateDTO) this.priorSingleSelectedTreeNode.getData()).getId() );
			
			this.wkRepo.reload( (WorkTemplateDTO) this.priorSingleSelectedTreeNode.getData() );
			
			
//			WorkTemplateDTO nodeData = (WorkTemplateDTO) this.priorSingleSelectedTreeNode.getData();
//			nodeData = mapper.map(reloadData, WorkTemplateDTO.class);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		this.priorSingleSelectedTreeNode = null;
		this.applyNodeChange(this.nextSingleSelectedTreeNode);
		return;
	}

	public void edit(){
		
//		this.workTemplateDTO = (WorkTemplateDTO) this.singleSelectedTreeNode.getData();
		if (  (!this.addingNode) && (  this.singleSelectedTreeNode == null || 
				(WorkTemplateDTO) this.singleSelectedTreeNode.getData() == null 
				|| ((WorkTemplateDTO) this.singleSelectedTreeNode.getData()).getId() == null
				|| ((WorkTemplateDTO) this.singleSelectedTreeNode.getData()).getId().intValue() <= 0) ) {
			addErrorMsg("Selecione o Workflow/Etapa que deseja editar.", FacesMessage.SEVERITY_WARN);
			return;
		}else{
			this.workTemplateDTO = (WorkTemplateDTO) this.singleSelectedTreeNode.getData();
		}

		this.setReadonly(false);
		
	}
	
	
	public void applyNodeChange(TreeNode newnode){
		if ( newnode == null){
			return;
		}
		
		this.priorSingleSelectedTreeNode = this.singleSelectedTreeNode;
		if ( this.priorSingleSelectedTreeNode != null) this.priorSingleSelectedTreeNode.setSelected(false);
		
		this.singleSelectedTreeNode = newnode;
		this.nextSingleSelectedTreeNode = null;
		
//		this.setReadonly(true);
		
		this.workTemplateDTO = (WorkTemplateDTO) singleSelectedTreeNode.getData();
		
		newnode.setExpanded(true);
		newnode.setSelected(true);
//		this.setReadonly(true);
		if( doubleClicked ) {
			doubleClickEvent(singleSelectedTreeNode);
		}else{
			
		}
		
	}

	/*
	public void cancelNodeChange() {
		this.nextSingleSelectedTreeNode.setSelected(false);
		this.singleSelectedTreeNode = this.priorSingleSelectedTreeNode;
//		this.onNodeSelect(this.singleSelectedTreeNode);
		this.setSingleSelectedTreeNode(singleSelectedTreeNode);
//		this.singleSelectedTreeNode.setSelected(false);
//		this.nextSingleSelectedTreeNode = null;
//		this.applyNodeChange(singleSelectedTreeNode);
		
		
	}*/
	
	
	public boolean getChanges(){
		if( this.priorSingleSelectedTreeNode == null ) return false;
		
		WorkTemplateDTO prior = (WorkTemplateDTO)this.priorSingleSelectedTreeNode.getData();
		if( prior == null ) return false;
		
		try {
			WorkTemplateDTO priorAtDatabase = this.wkRepo.load(prior.getId());
			
			if (priorAtDatabase.equals(prior)) return false;
		} catch (Exception e) {
			processErros( e );
			return true;
		} 
		
		return true;
	}
	
	public void doubleClickEvent(TreeNode treenode) {
		WorkTemplateDTO newWorkDTO = new WorkTemplateDTO();
		
		newWorkDTO.setWorkTemplateSuper(this.workTemplateDTO);
		this.workTemplateDTO = newWorkDTO;
		
		TreeNode node = new DefaultTreeNode(newWorkDTO, treenode);
		this.doubleClicked = false;
		this.applyNodeChange(node);
		addingNode=true;
		this.edit();
		addingNode=false;
	}
/*
	public void onNodeUnSelect(NodeUnselectEvent event) {
		System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: UnSelected");
	}

	public void onNodeExpand(NodeExpandEvent event) {
		System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: Expanded");
	}

	public void onNodeCollapse(NodeCollapseEvent event) {
		System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: Collapsed");
	}
*/
	@Transactional
	public void delete() throws Exception {
		try {
			if (this.getWorkTemplateDTO() == null || this.getWorkTemplateDTO().getId() == null
					|| this.getWorkTemplateDTO().getId().intValue() <= 0) {
				addErrorMsg("Selecione o Modelo que deseja Excluir.", FacesMessage.SEVERITY_WARN);
				return;
			}

/*			if ( !this.singleSelectedTreeNode.isLeaf() ){
				addErrorMsg("Esta etapa do fluxo possui etapas abaixo.  Remova-as primeiro.", FacesMessage.SEVERITY_WARN);
				return;
			}*/
			
			this.wkRepo.delete(getWorkTemplateDTO());
			
			TreeNode parent = this.singleSelectedTreeNode.getParent();
			if ( parent != null ){
				parent.getChildren().remove(singleSelectedTreeNode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if (e instanceof CustomException) {
				addErrorMsg(((CustomException) e).getErros());
			} else {
				StackTraceElement[] trace = e.getStackTrace();
				addErrorMsg("Erro interno: " + trace[0].toString(), FacesMessage.SEVERITY_ERROR);
			}

			return;
		} finally {

		}

		this.listTemplates = null;
		this.workTemplateDTO = new WorkTemplateDTO();
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");

		return;

	}
	
	public String headerConfirmDialog(){

		if ( priorSingleSelectedTreeNode != null){
			return ((WorkTemplateDTO)priorSingleSelectedTreeNode.getData()).getDescription();
			
		}else if (this.workTemplateDTO != null ) {
			return this.workTemplateDTO.getDescription();
		}

		return "";
		 
	}
	
	@Transactional
	public void update()  {

		try {
			if ( priorSingleSelectedTreeNode != null){
				
//				boolean lb_novo_registro=true;
//				if (((WorkTemplateDTO)priorSingleSelectedTreeNode.getData()).getId() > 0 ) lb_novo_registro = false;
				
				WorkTemplateDTO updata = this.wkRepo.update((WorkTemplateDTO)priorSingleSelectedTreeNode.getData());
				((WorkTemplateDTO)priorSingleSelectedTreeNode.getData()).setId(updata.getId());
				
				if ( this.nextSingleSelectedTreeNode == null ){
					nextSingleSelectedTreeNode = priorSingleSelectedTreeNode;
				}
				this.applyNodeChange(this.nextSingleSelectedTreeNode);
			}else{
				this.wkRepo.update(this.workTemplateDTO);
			}
		} catch (Exception e) {
			/*if ( nextSingleSelectedTreeNode != null){
				cancelNodeChange();
			}*/
			processErros( e );
			return;
		} 
		
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");
		this.setReadonly(true);
		listTemplates = null;
		
		return;
	}

	public List<UserGroupDTO> getUserGroups(){
		if ( userGroups == null ) userGroups = ugRepo.listAll();
		return userGroups;
	}
	
}
