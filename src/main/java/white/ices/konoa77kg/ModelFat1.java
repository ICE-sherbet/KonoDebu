package white.ices.konoa77kg;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelFat1 extends ModelPlayer {
    ModelRenderer Shape1;

    ModelRenderer Shape2;

    ModelRenderer lleg;

    ModelRenderer rleg;

    ModelRenderer larm;
    ModelRenderer rarm;

    public int scale = 0;

    public int mod1 = 0;

    public int mod2 = 0;

    public int mod3 = 0;

    public static int bellyFatModifier;

    public ModelFat1(float modelSize, boolean smallArmsIn, int bellyFat) {
        super(modelSize, smallArmsIn);

        this.isChild = false;
        this.Shape1 = new ModelRenderer((ModelBase)this, 17, 23);
        this.Shape2 = new ModelRenderer((ModelBase)this, 17, 23);
        this.lleg = new ModelRenderer((ModelBase)this, 16, 48);
        this.rleg = new ModelRenderer((ModelBase)this, 0, 16);
        this.larm = new ModelRenderer((ModelBase)this, 32, 48);
        this.rarm = new ModelRenderer((ModelBase)this, 40, 16);
        bellyFatModifier = bellyFat / 2;
        if (bellyFatModifier > 32 || true) {
            int modifiedFat = 0;
            this.Shape1.addBox(0.0F, 0.0F, -5.0F - modifiedFat, 6, 5, 3 + modifiedFat);
            if (smallArmsIn) {
                this.larm.addBox(0.5F, -2.0F, -2.0F, 3, 12, 4, 1.6F);
                this.rarm.addBox(-3.5F, -2.0F, -2.0F, 3, 12, 4, 1.6F);
                this.lleg.addBox(-0.5F, 0.0F, -2.0F, 4, 12, 4, 1.6F);
                this.rleg.addBox(-3.5F, 0.0F, -2.0F, 4, 12, 4, 1.6F);
            } else {
                this.larm.addBox(0.5F, -2.0F, -2.0F, 4, 12, 4, 1.6F);
                this.rarm.addBox(-4.5F, -2.0F, -2.0F, 4, 12, 4, 1.6F);
                this.lleg.addBox(-0.5F, 0.0F, -2.0F, 4, 12, 4, 1.6F);
                this.rleg.addBox(-3.5F, 0.0F, -2.0F, 4, 12, 4, 1.6F);
            }
            this.Shape2.addBox(-1.0F, -2.0F, -4.0F - modifiedFat, 8, 7, 2 + modifiedFat);
        } else if (bellyFatModifier > 28 && bellyFatModifier < 33) {
            this.Shape1.addBox((bellyFatModifier > 13) ? 0.0F : 1.0F, 0.0F, -4.0F, (bellyFatModifier > 13) ? 6 : 4, (bellyFatModifier > 13) ? 5 : 4, 2);
            if (smallArmsIn) {
                this.larm.addBox(0.0F, -2.0F, -2.0F, 3, 12, 4, 1.01F);
                this.rarm.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 4, 1.01F);
                this.lleg.addBox(-1.0F, 0.0F, -2.0F, 4, 12, 4, 1.01F);
                this.rleg.addBox(-3.0F, 0.0F, -2.0F, 4, 12, 4, 1.01F);
            } else {
                this.larm.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 1.01F);
                this.rarm.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, 1.01F);
                this.lleg.addBox(-1.0F, 0.0F, -2.0F, 4, 12, 4, 1.01F);
                this.rleg.addBox(-3.0F, 0.0F, -2.0F, 4, 12, 4, 1.01F);
            }
            this.Shape2.addBox((bellyFatModifier > 13) ? -1.0F : 0.0F, -2.0F, (bellyFatModifier > 13) ? -3.0F : -3.0F, (bellyFatModifier > 13) ? 8 : 6, 7, 1);
        } else if (bellyFatModifier > 24 && bellyFatModifier < 29) {
            this.Shape1.addBox(1.0F, 0.0F, -4.0F, 4, 5, 2);
            if (smallArmsIn) {
                this.larm.addBox(-0.5F, -2.0F, -2.0F, 3, 12, 4, 0.6F);
                this.rarm.addBox(-2.5F, -2.0F, -2.0F, 3, 12, 4, 0.6F);
                this.lleg.addBox(-1.5F, 0.0F, -2.0F, 4, 12, 4, 0.6F);
                this.rleg.addBox(-2.5F, 0.0F, -2.0F, 4, 12, 4, 0.6F);
            } else {
                this.larm.addBox(-0.5F, -2.0F, -2.0F, 4, 12, 4, 0.6F);
                this.rarm.addBox(-3.5F, -2.0F, -2.0F, 4, 12, 4, 0.6F);
                this.lleg.addBox(-1.5F, 0.0F, -2.0F, 4, 12, 4, 0.6F);
                this.rleg.addBox(-2.5F, 0.0F, -2.0F, 4, 12, 4, 0.6F);
            }
            this.Shape2.addBox(0.0F, -1.0F, -3.0F, 6, 6, 1);
        } else if (bellyFatModifier > 20 && bellyFatModifier < 25) {
            this.Shape1.addBox(0.0F, 0.0F, 0.0F, 4, 5, 0);
            if (smallArmsIn) {
                this.larm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
                this.rarm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
                this.lleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
            } else {
                this.larm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
                this.lleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
            }
            this.Shape2.addBox(0.0F, 0.0F, -3.0F, 6, 5, 2);
        } else {
            this.Shape1.addBox(0.0F, 0.0F, 0.0F, 4, 5, 0);
            if (smallArmsIn) {
                this.larm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
                this.rarm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
                this.lleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
            } else {
                this.larm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
                this.lleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
                this.rleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
            }
            this.Shape2.addBox(0.0F, 0.0F, 0.0F, 6, 5, 0);
        }
        this.Shape1.setRotationPoint(-3.0F, 6.0F, 0.0F);
        this.Shape1.showModel = true;
        setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
        this.lleg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.lleg.showModel = true;
        this.rleg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.rleg.showModel = true;
        this.larm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.larm.showModel = true;
        setRotation(this.larm, 0.0F, 0.0F, 0.0F);
        this.rarm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rarm.showModel = true;
        setRotation(this.rarm, 0.0F, 0.0F, 0.0F);
        this.Shape2.setRotationPoint(-3.0F, 6.0F, 0.0F);
        this.Shape2.showModel = true;
        setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
        this.bipedLeftArm = this.larm;
        this.bipedRightArm = this.rarm;
        this.bipedLeftLeg = this.lleg;
        this.bipedRightLeg = this.rleg;
    }

    protected ModelRenderer getArmForSide(EnumHandSide side) {
        return (side == EnumHandSide.LEFT) ? this.larm : this.rarm;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Shape1.render(f5);
        this.bipedBody.addChild(this.Shape1);
        this.Shape2.render(f5);

        this.bipedBody.addChild(this.Shape2);
        this.bipedLeftLegwear.render(f5);
        this.bipedRightLegwear.render(f5);
        this.bipedLeftArmwear.render(f5);
        this.bipedRightArmwear.render(f5);
        this.bipedBodyWear.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public float getScale() {
        return this.scale;
    }
}

