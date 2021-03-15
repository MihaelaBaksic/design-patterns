	.file	"time.cpp"
	.text
	.section	.text._ZN13PlainOldClass3setEi,"axG",@progbits,PlainOldClass::set(int),comdat
	.align 2
	.weak	PlainOldClass::set(int)
	.type	PlainOldClass::set(int), @function
PlainOldClass::set(int):
.LFB0:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)
	movl	%esi, -12(%rbp)
	movq	-8(%rbp), %rax
	movl	-12(%rbp), %edx
	movl	%edx, (%rax)
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	PlainOldClass::set(int), .-PlainOldClass::set(int)
	.section	.text._ZN9CoolClass3setEi,"axG",@progbits,CoolClass::set(int),comdat
	.align 2
	.weak	CoolClass::set(int)
	.type	CoolClass::set(int), @function
CoolClass::set(int):
.LFB2:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)
	movl	%esi, -12(%rbp)
	movq	-8(%rbp), %rax
	movl	-12(%rbp), %edx
	movl	%edx, 8(%rax)
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	CoolClass::set(int), .-CoolClass::set(int)
	.section	.text._ZN9CoolClass3getEv,"axG",@progbits,CoolClass::get(),comdat
	.align 2
	.weak	CoolClass::get()
	.type	CoolClass::get(), @function
CoolClass::get():
.LFB3:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rax
	movl	8(%rax), %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE3:
	.size	CoolClass::get(), .-CoolClass::get()
	.section	.text._ZN4BaseC2Ev,"axG",@progbits,Base::Base(),comdat
	.align 2
	.weak	Base::Base()
	.type	Base::Base(), @function
Base::Base():
.LFB7:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)
	leaq	16+vtable for Base(%rip), %rdx
	movq	-8(%rbp), %rax
	movq	%rdx, (%rax)
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE7:
	.size	Base::Base(), .-Base::Base()
	.weak	Base::Base()
	.set	Base::Base(),Base::Base()
	.section	.text._ZN9CoolClassC2Ev,"axG",@progbits,CoolClass::CoolClass(),comdat
	.align 2
	.weak	CoolClass::CoolClass()
	.type	CoolClass::CoolClass(), @function
CoolClass::CoolClass():
.LFB9:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rax
	movq	%rax, %rdi
	call	Base::Base()
	leaq	16+vtable for CoolClass(%rip), %rdx
	movq	-8(%rbp), %rax
	movq	%rdx, (%rax)
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE9:
	.size	CoolClass::CoolClass(), .-CoolClass::CoolClass()
	.weak	CoolClass::CoolClass()
	.set	CoolClass::CoolClass(),CoolClass::CoolClass()
	.text
	.globl	main
	.type	main, @function
main:
.LFB4:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$40, %rsp
	.cfi_offset 3, -24
	movq	%fs:40, %rax
	movq	%rax, -24(%rbp)
	xorl	%eax, %eax
	movl	$16, %edi
	call	operator new(unsigned long)@PLT
	movq	%rax, %rbx
	movq	%rbx, %rdi
	call	CoolClass::CoolClass()
	movq	%rbx, -32(%rbp)
	leaq	-36(%rbp), %rax
	movl	$42, %esi
	movq	%rax, %rdi
	call	PlainOldClass::set(int)
	movq	-32(%rbp), %rax
	movq	(%rax), %rax
	movq	(%rax), %rdx
	movq	-32(%rbp), %rax
	movl	$42, %esi
	movq	%rax, %rdi
	call	*%rdx
	movl	$0, %eax
	movq	-24(%rbp), %rcx
	xorq	%fs:40, %rcx
	je	.L9
	call	__stack_chk_fail@PLT
.L9:
	addq	$40, %rsp
	popq	%rbx
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	main, .-main
	.weak	vtable for CoolClass
	.section	.data.rel.ro.local._ZTV9CoolClass,"awG",@progbits,vtable for CoolClass,comdat
	.align 8
	.type	vtable for CoolClass, @object
	.size	vtable for CoolClass, 32
vtable for CoolClass:
	.quad	0
	.quad	typeinfo for CoolClass
	.quad	CoolClass::set(int)
	.quad	CoolClass::get()
	.weak	vtable for Base
	.section	.data.rel.ro._ZTV4Base,"awG",@progbits,vtable for Base,comdat
	.align 8
	.type	vtable for Base, @object
	.size	vtable for Base, 32
vtable for Base:
	.quad	0
	.quad	typeinfo for Base
	.quad	__cxa_pure_virtual
	.quad	__cxa_pure_virtual
	.weak	typeinfo for CoolClass
	.section	.data.rel.ro._ZTI9CoolClass,"awG",@progbits,typeinfo for CoolClass,comdat
	.align 8
	.type	typeinfo for CoolClass, @object
	.size	typeinfo for CoolClass, 24
typeinfo for CoolClass:
	.quad	vtable for __cxxabiv1::__si_class_type_info+16
	.quad	typeinfo name for CoolClass
	.quad	typeinfo for Base
	.weak	typeinfo name for CoolClass
	.section	.rodata._ZTS9CoolClass,"aG",@progbits,typeinfo name for CoolClass,comdat
	.align 8
	.type	typeinfo name for CoolClass, @object
	.size	typeinfo name for CoolClass, 11
typeinfo name for CoolClass:
	.string	"9CoolClass"
	.weak	typeinfo for Base
	.section	.data.rel.ro._ZTI4Base,"awG",@progbits,typeinfo for Base,comdat
	.align 8
	.type	typeinfo for Base, @object
	.size	typeinfo for Base, 16
typeinfo for Base:
	.quad	vtable for __cxxabiv1::__class_type_info+16
	.quad	typeinfo name for Base
	.weak	typeinfo name for Base
	.section	.rodata._ZTS4Base,"aG",@progbits,typeinfo name for Base,comdat
	.type	typeinfo name for Base, @object
	.size	typeinfo name for Base, 6
typeinfo name for Base:
	.string	"4Base"
	.ident	"GCC: (Ubuntu 9.3.0-17ubuntu1~20.04) 9.3.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
0:
	.string	 "GNU"
1:
	.align 8
	.long	 0xc0000002
	.long	 3f - 2f
2:
	.long	 0x3
3:
	.align 8
4:
